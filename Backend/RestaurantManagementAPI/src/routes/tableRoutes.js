import {
    getAllTables,
    createTable,
    getTableDetail,
    openTable,
    addFoodstoTable,
    getAllOrderedFoodByRole,
    changeStatusOfOrder,
    getOrderSummary,
    closeTable
} from '../controllers/tableController';

import { loginRequired } from '../controllers/userController';

const routes = (app) => {

    app.route('/table/getAllTables')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, getAllTables);

    //}, loginRequired, getAllTables);

    app.route('/table/createTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, createTable);
    //}, loginRequired, createTable);

    app.route('/table/openTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, openTable);

    app.route('/table/getTableDetail')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, getTableDetail);
    //}, loginRequired, getTableDetail);


    app.route('/table/getOrderSummary')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, getOrderSummary);

    app.route('/table/addFoodsToTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, addFoodstoTable);

    app.route('/table/getAllOrderedFoodByRole')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, getAllOrderedFoodByRole);

    app.route('/table/changeStatusOfOrder')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, changeStatusOfOrder);

    app.route('/table/closeTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`);
            console.log(`Request type: ${req.method}`);
            next();
        }, closeTable);
}

export default routes;
