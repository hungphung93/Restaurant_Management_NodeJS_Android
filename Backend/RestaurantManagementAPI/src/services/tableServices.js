import * as tableRepositories from '../repositories/tableRepositories';
import { Table } from '../models/Table';
import { Transaction } from '../models/Transaction';
import { OrderedFoodInfo } from '../models/OrderdFoodInfo';

export const getAllTables = async () => {
  try {
    let lstTableEntities = await tableRepositories.getAllTables();

    let lstTable = lstTableEntities.map((x) => {
      return new Table(x._id, x.name, x.status, x.open_at);
    });

    return lstTable;
  } catch (err) {
    throw err;
  }
};

export const createTable = async (tableName) => {
  try {
    let createdTable = await tableRepositories.createTable(tableName);

    return await new Table(
      createdTable._id,
      createdTable.name,
      createdTable.status,
      createdTable.open_at
    );
  } catch (err) {
    throw err;
  }
};

export const getTableDetail = async (tableName) => {
  try {
    let tableInfo = await tableRepositories.getTableDetail(tableName);

    console.log(tableInfo);

    return await new Transaction(
      tableInfo._id,
      tableInfo.table_name,
      tableInfo.status,
      tableInfo.open_at,
      tableInfo.ordered_foods,
      tableInfo.total_amount
    );
  } catch (err) {
    throw err;
  }
};

export const openTable = async (tableName) => {
  try {
    let isOpened = await tableRepositories.openTable(tableName);

    return await isOpened;
  } catch (err) {
    throw err;
  }
};

export const addOrderToTable = async (tableName, lstFood) => {
  try {
    let isAdded = await tableRepositories.addOrderToTable(tableName, lstFood);

    return await isAdded;
  } catch (err) {
    throw err;
  }
};

export const getAllOrderedFoodByRole = async (role) => {
  try {
    let orderedFoods = await tableRepositories.getAllOrderedFoodByRole(role);

    let orderedFoodInfo = [];

    // Mapping two array entities to add food information to transaction

    // Firstly, loop over all transaction to select all ordered Food
    // Then use the foodId to map food information
    orderedFoods.orderedFoods.map((transaction) => {
      transaction.ordered_foods.map((orderedFood) => {
        let orderedFoodId = orderedFood.food_id;

        let foodInfoEntity = orderedFoods.foodInfo
          .filter((food) => {
            return food._id == orderedFoodId;
          })
          .map((x) => {
            return { foodId: x._id, foodName: x.name, imageURL: x.image_url };
          })[0];

        let foodInfo = new OrderedFoodInfo(
          transaction._id,
          transaction.table_name,
          orderedFoodId,
          foodInfoEntity.foodName,
          foodInfoEntity.imageURL,
          orderedFood.status,
          orderedFood.quantity,
          orderedFood.price
        );

        orderedFoodInfo.push(foodInfo);
      });
    });

    return await orderedFoodInfo;
  } catch (err) {
    throw err;
  }
};
