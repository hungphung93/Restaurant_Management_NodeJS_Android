import * as tableRepositories from '../repositories/tableRepositories';
import { Table } from '../models/Table';
import { Transaction } from '../models/Transaction';
import { OrderedFoodInfo } from '../models/OrderdFoodInfo';
import { FoodStatus } from '../shared/enum/FoodStatus';
import { Role } from '../shared/enum/Role';

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

    let orderedFoodInfo = [];

    // Mapping two array entities to add food information to transaction

    // Firstly, loop over all transaction to select all ordered Food
    // Then use the foodId to map food information

    tableInfo.transaction.ordered_foods.map((orderedFood) => {
      let orderedFoodId = orderedFood.food_id;

      let foodInfoEntity = tableInfo.foodInfo
        .filter((food) => {
          return food._id == orderedFoodId;
        })
        .map((x) => {
          return { foodId: x._id, foodName: x.name, imageURL: x.image_url };
        })[0];

      let foodInfo = new OrderedFoodInfo(
        tableInfo.transaction._id,
        tableInfo.transaction.table_name,
        orderedFood.order_id,
        orderedFoodId,
        foodInfoEntity.foodName,
        foodInfoEntity.imageURL,
        orderedFood.status,
        orderedFood.quantity,
        orderedFood.price,
        orderedFood.updated_at
      );

      orderedFoodInfo.push(foodInfo);
    });

    return await new Transaction(
      tableInfo.transaction._id,
      tableInfo.transaction.table_name,
      tableInfo.transaction.status,
      tableInfo.transaction.open_at,
      orderedFoodInfo,
      tableInfo.transaction.total_amount
    );

  } catch (err) {
    throw err;
  }
};

export const getOrderSummary = async (tableName) => {
  let transactionInfo = await getTableDetail(tableName);

  let orderedFoods = transactionInfo.orderedFoods;

  transactionInfo.orderedFoods = [];

  let groupedOrderFoods = [];

  orderedFoods.forEach(order => {
    // If this is a new order (unique order), then simply add to the group
    if (groupedOrderFoods[order.foodId] == undefined) {
      groupedOrderFoods[order.foodId] = order;
    }
    // Otherwise, find the added group and update the quantity
    else {
      let groupedOrder = groupedOrderFoods[order.foodId];

      groupedOrder.quantity += order.quantity;

      groupedOrderFoods[order.foodId] = groupedOrder;
    }
  });

  for (var foodId in groupedOrderFoods) {
    transactionInfo.orderedFoods.push(groupedOrderFoods[foodId]);
  }

  return await transactionInfo;
}

export const openTable = async (tableName) => {
  try {
    let isOpened = await tableRepositories.openTable(tableName);

    return await isOpened;
  } catch (err) {
    throw err;
  }
};

export const closeTable = async (tableName) => {
  try {
    // Cancel all orders which haven't been served yet and close the transaction
    let isOrdersCancelled = await tableRepositories.cancelAllPendingOrder(tableName);

    if (!isOrdersCancelled) {
      return await (false);
    }

    // Close the table
    let isTableClosed = await tableRepositories.closeTable(tableName);

    return await isTableClosed;

  } catch (err) {
    throw err;
  }
}

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
          orderedFood.order_id,
          orderedFoodId,
          foodInfoEntity.foodName,
          foodInfoEntity.imageURL,
          orderedFood.status,
          orderedFood.quantity,
          orderedFood.price,
          orderedFood.updated_at
        );

        if (role == Role.COOK && foodInfo.foodStatus != FoodStatus.READY && foodInfo.foodStatus != FoodStatus.SERVED)
          orderedFoodInfo.push(foodInfo);
        else if (role != Role.COOK && foodInfo.foodStatus != FoodStatus.SERVED)
          orderedFoodInfo.push(foodInfo);
      });
    });

    console.log(role);

    if (role == Role.WAITER)
      orderedFoodInfo.sort((a, b) => waiterSort);
    else
      orderedFoodInfo.sort((a, b) => { return a.updated_at - b.updated_at; });


    return await orderedFoodInfo;
  } catch (err) {
    throw err;
  }
};

export const changeStatusOfOrder = async (transactionId, orderId, status) => {
  let postStatus = mappingStatus(status);

  return await tableRepositories.changeStatusOfOrder(transactionId, orderId, postStatus);
}

const mappingStatus = (preStatus) => {
  let postStatus = FoodStatus.ONLINE;

  switch (preStatus) {
    case FoodStatus.ONLINE:
      postStatus = FoodStatus.COOKING;
      break;
    case FoodStatus.COOKING:
      postStatus = FoodStatus.READY;
      break;
    case FoodStatus.READY:
      postStatus = FoodStatus.SERVED;
      break;
  }

  return postStatus;
}

const waiterSort = (orderA, orderB) => {
  if (orderA.foodStatus == orderB.foodStatus)
    return orderA.updated_at - orderB.updated_at;

  if (orderA.foodStatus == FoodStatus.SERVED)
    return -1;

  return orderA.updated_at - orderB.updated_at;
}