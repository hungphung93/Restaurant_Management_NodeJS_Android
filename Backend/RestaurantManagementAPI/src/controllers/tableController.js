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

export const getOrderSummary = async (req, res) => {
    try {
        let data = req.body;
        let tableName = data.tableName;

        let tableOrderSummary = await tableServices.getOrderSummary(tableName);

        return await res.status(200).send(new HttpResponseResult(true, "", tableOrderSummary));

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

export const closeTable = async (req, res) => {
    try {
        let data = req.body;
        let tableName = data.tableName;

        let isTableClosed = await tableServices.closeTable(tableName);

        return await res.status(200).send(new HttpResponseResult(true, "", isTableClosed));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}

export const addFoodstoTable = async (req, res) => {
    try {
        let data = req.body;

        let tableName = data.tableName;
        let lstFood = data.listFood;

        let isAdded = await tableServices.addOrderToTable(tableName, lstFood);

        return await res.status(200).send(new HttpResponseResult(true, "", isAdded));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}

export const getAllOrderedFoodByRole = async (req, res) => {
    try {
        let data = req.body;

        let role = data.role;

        let orderedFoods = await tableServices.getAllOrderedFoodByRole(role);

        return await res.status(200).send(new HttpResponseResult(true, "", orderedFoods));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}

export const changeStatusOfOrder = async (req, res) => {
    try {
        let data = req.body;

        let transactionId = data.transactionId;
        let orderId = data.orderId;
        let status = data.status;

        let isUpdated = await tableServices.changeStatusOfOrder(transactionId, orderId, status);

        return await res.status(200).send(new HttpResponseResult(true, "", isUpdated));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}