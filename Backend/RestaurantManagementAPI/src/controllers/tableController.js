import * as tableServices from '../services/tableServices';
import { logger } from '../shared/utilities/loggingUtilities';
import HttpResponseResult from '../shared/models/HttpResponseResult';

export const getAllTables = async (req, res) => {

    try {
        let lstTables = await tableServices.getAllTables();

        return await res.status(200).send(new HttpResponseResult(true, "", lstTables));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
};

export const createTable = async (req, res) => {
    try {
        let data = req.body;

        let tableName = data.tableName;

        let createdTable = await tableServices.createTable(tableName);

        return await res.status(200).send(new HttpResponseResult(true, "", createdTable));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}

export const getTableDetail = async (req, res) => {
    try {
        let data = req.body;
        let tableName = data.tableName;

        let tableInfo = await tableServices.getTableDetail(tableName);

        return await res.status(200).send(new HttpResponseResult(true, "", tableInfo));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}

export const openTable = async (req, res) => {
    try {
        let data = req.body;
        let tableName = data.tableName;

        let isOpened = await tableServices.openTable(tableName);

        return await res.status(200).send(new HttpResponseResult(true, "", isOpened));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}