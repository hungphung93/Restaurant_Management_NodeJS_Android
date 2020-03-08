import { TableEntity } from '../entities/TableEntity';


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

export const getTableDetail = async (tableId) => {
    try {
        let tableDetail = await TableEntity.findOne({ _id: tableId });

        return await tableDetail;
    } catch (err) {
        throw err;
    }
}