import {
    getAllFoods
} from '../controllers/foodController';

import { loginRequired } from '../controllers/userController';

const routes = (app) => {
    app.route('/food/getAllFoods')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, loginRequired, getAllFoods);

}



export default routes;
