import * as tableRepositories from '../repositories/tableRepositories';
import { Table } from '../models/Table';
import { Transaction } from '../models/Transaction';

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
}

export const createTable = async (tableName) => {
    try {
        let createdTable = await tableRepositories.createTable(tableName);

        return await new Table(createdTable._id, createdTable.name, createdTable.status, createdTable.open_at);
    } catch (err) {
        throw err;
    }
}

export const getTableDetail = async (tableName) => {
    try {
        let tableInfo = await tableRepositories.getTableDetail(tableName);

        return await new Transaction(tableInfo._id, tableInfo.table_name,
            tableInfo.status,
            tableInfo.open_at,
            tableInfo.orderedFoods,
            tableInfo.total_amount);
    } catch (err) {
        throw err;
    }
}

export const openTable = async (tableName) => {
    try {
        let isOpened = await tableRepositories.openTable(tableName);

        return await isOpened;
    } catch (err) {
        throw err;
    }
}