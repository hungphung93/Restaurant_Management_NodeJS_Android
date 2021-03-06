import { TableEntity } from '../entities/TableEntity';
import { TransactionEntity } from '../entities/TransactionEntity';
import { TransactionStatus } from '../shared/enum/TransactionStatus';
import { TableStatus } from '../shared/enum/TableStatus';
import { OrderedFood } from '../entities/OrderedFood';
import mongoose, { Types, mongo } from 'mongoose';
import { FoodStatus } from '../shared/enum/FoodStatus';
import {
  transactionCollection,
  orderedFoodCollection,
} from '../shared/constants/mongoDBEntityNames';
import { FoodEntity } from '../entities/FoodEntity';

export const getAllTables = async () => {
  try {
    let lstTables = await TableEntity.find();

    return await lstTables;
  } catch (err) {
    throw err;
  }
};

export const createTable = async (tableName) => {
  try {
    let newTable = new TableEntity();
    newTable.name = tableName;

    const createdTable = await newTable.save();

    return await createdTable;
  } catch (err) {
    throw err;
  }
};

export const getTableDetail = async (tableName) => {
  try {
    let transaction = await TransactionEntity.findOne({
      table_name: tableName,
      status: TransactionStatus.PROCESSING,
    });

    let lstFoodIds = [];

    if (transaction != null) {
      transaction.ordered_foods.map((food) => {
        lstFoodIds.push(food.food_id);
      });
    }

    let foodInfo = await FoodEntity.find({ _id: { $in: lstFoodIds } }).select(
      'name image_url'
    );

    return await { transaction: transaction, foodInfo: foodInfo };
  } catch (err) {
    throw err;
  }
};

export const openTable = async (tableName) => {
  try {
    let table = await TableEntity.findOne({ name: tableName });

    table.status = TableStatus.SERVING;
    table.open_at = Date.now();

    // Change table Status
    await table.save();

    let transaction = new TransactionEntity();
    transaction.table_name = tableName;
    transaction.ordered_foods = [];

    // Add new transaction
    await transaction.save();

    return await true;
  } catch (err) {
    throw err;
  }
};

export const cancelAllPendingOrder = async (tableName) => {
  try {
    let transaction = await TransactionEntity.findOne({
      table_name: tableName,
      status: TransactionStatus.PROCESSING,
    });

    if (transaction == null) {
      return await false;
    }

    transaction.ordered_foods.forEach((order) => {
      if (order.status != FoodStatus.SERVED) {
        order.status = FoodStatus.CANCELED;
        order.updated_at = new Date();
      }
    });

    transaction.status = TransactionStatus.COMPLETED;

    // Update transaction
    transaction.markModified('status');
    transaction.markModified('ordered_foods');
    await transaction.save();

    return await true;
  } catch (err) {
    throw err;
  }
};

export const closeTable = async (tableName) => {
  try {
    // Update table status
    let table = await TableEntity.findOne({ name: tableName });

    if (table == null) return await false;

    table.status = TableStatus.EMPTY;
    table.open_at = null;

    await table.save();

    return await true;
  } catch (err) {
    throw err;
  }
};

export const addOrderToTable = async (tableName, lstFood) => {
  try {
    let cur = new Date();

    let lstOrderedFood = lstFood.map((x) => {
      let orderId = new mongoose.Types.ObjectId();
      return new OrderedFood(
        orderId,
        x.foodId,
        FoodStatus.ONLINE,
        x.quantity,
        x.price,
        cur
      );
    });

    let total_additional_amount = lstFood.reduce(
      (currentAmount, food) => currentAmount + food.quantity * food.price,
      0
    );

    let transaction = await TransactionEntity.findOne({
      table_name: tableName,
      status: TransactionStatus.PROCESSING,
    });

    transaction.ordered_foods = transaction.ordered_foods.concat(
      lstOrderedFood
    );

    transaction.total_amount =
      transaction.total_amount + total_additional_amount;

    await transaction.save();

    return await true;
  } catch (err) {
    throw err;
  }
};

export const getAllOrderedFoodByRole = async (role) => {
  try {
    let orderedFoods = await TransactionEntity.find({
      status: TransactionStatus.PROCESSING,
    }).select('table_name ordered_foods');

    let lstFoodIds = [];

    orderedFoods.map((transaction) => {
      transaction.ordered_foods.map((food) => {
        lstFoodIds.push(food.food_id);
      });
    });

    let foodInfo = await FoodEntity.find({ _id: { $in: lstFoodIds } }).select(
      'name image_url'
    );

    return await { orderedFoods: orderedFoods, foodInfo: foodInfo };
  } catch (err) {
    throw err;
  }
};

export const changeStatusOfOrder = async (transactionId, orderId, status) => {
  try {
    let transaction = await TransactionEntity.findOne({
      _id: transactionId,
      'ordered_foods.order_id': new mongoose.Types.ObjectId(orderId),
    });

    if (transaction == null) return await false;

    transaction.ordered_foods.forEach((order) => {
      if (order.order_id == orderId) {
        order.status = status;
        order.updated_at = new Date();
      }
    });

    transaction.markModified('ordered_foods');

    await transaction.save();

    return await true;
  } catch (err) {
    throw err;
  }
};
