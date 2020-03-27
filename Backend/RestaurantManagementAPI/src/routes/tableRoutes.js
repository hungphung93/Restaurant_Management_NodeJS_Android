import {
    getAllTables,
    createTable,
    getTableDetail,
    openTable
} from '../controllers/tableController';

import { loginRequired } from '../controllers/userController';

const routes = (app) => {
    app.route('/table/getAllTables')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, getAllTables);

    //}, loginRequired, getAllTables);

    app.route('/table/createTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, createTable);
    //}, loginRequired, createTable);

    app.route('/table/openTable')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, openTable);



    app.route('/table/getTableDetail')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, getTableDetail);
    //}, loginRequired, getTableDetail);
}


export default routes;
