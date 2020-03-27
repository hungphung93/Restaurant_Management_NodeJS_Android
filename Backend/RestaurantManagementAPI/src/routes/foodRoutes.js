import {
    getAllFoods, addNewFood
} from '../controllers/foodController';

import { loginRequired } from '../controllers/userController';

const routes = (app) => {
    app.route('/food/getAllFoods')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, getAllFoods);
    //}, loginRequired, getAllFoods);

    app.route('/food/addNewFood')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, addNewFood);
    //}, loginRequired, getAllFoods);
}



export default routes;
