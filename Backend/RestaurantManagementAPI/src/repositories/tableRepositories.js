import { TableEntity } from '../entities/TableEntity';
import { TransactionEntity } from '../entities/TransactionEntity';
import { TransactionStatus } from '../shared/enum/TransactionStatus';
import { TableStatus } from '../shared/enum/TableStatus';
import { Transaction } from '../models/Transaction';


export const getAllTables = async () => {
    try {
        let lstTables = await TableEntity.find();

        return await lstTables;
    } catch (err) {
        throw err;
    }
}

export const createTable = async (tableName) => {
    try {
        let newTable = new TableEntity();
        newTable.name = tableName;

        const createdTable = await newTable.save();

        return await createdTable;
    } catch (err) {
        throw err;
    }
}

export const getTableDetail = async (tableName) => {
    try {
        let transaction = await TransactionEntity.findOne({ table_name: tableName, status: TransactionStatus.PROCESSING });

        return await transaction;
    } catch (err) {
        throw err;
    }
}

export const openTable = async (tableName) => {
    try {
        let table = await TableEntity.findOne({ name: tableName });

        table.status = TableStatus.SERVING;
        table.open_at = Date.now();

        // Change table Status
        await table.save();

        let transaction = new TransactionEntity();
        transaction.table_name = tableName;

        // Add new transaction
        await transaction.save();

        return await true;

    } catch (err) {
        throw err;
    }
}