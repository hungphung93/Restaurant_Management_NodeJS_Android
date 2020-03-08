import {
    getAllTables,
    createTable,
    getTableDetail
} from '../controllers/tableController';

import { loginRequired } from '../controllers/userController';

const routes = (app) => {
    app.route('/table/getAllTables')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, loginRequired, getAllTables);

    app.route('/table/createTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, loginRequired, createTable);

    app.route('/table/getTableDetail')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, loginRequired, getTableDetail);
}



export default routes;
