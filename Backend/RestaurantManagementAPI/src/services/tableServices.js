import * as tableRepositories from '../repositories/tableRepositories';
import { Table } from '../models/Table';

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

        return new Table(createdTable._id, createdTable.name, createdTable.status, createdTable.open_at);
    } catch (err) {
        throw err;
    }
}

export const getTableDetail = async (tableId) => {
    try {
        let tableInfo = await tableRepositories.getTableDetail(tableId);

        return new Table(tableInfo._id, tableInfo.name, tableInfo.status, tableInfo.open_at);
    } catch (err) {
        throw err;
    }
}