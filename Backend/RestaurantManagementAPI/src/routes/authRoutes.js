import {
    register,
    login
} from '../controllers/userController';

const routes = (app) => {
    app.route('/auth/register')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, register);

    app.route('/auth/login')
        .post((req, res, next) => {
            // middleware
            console.log(`Request from: ${req.originalUrl}`)
            console.log(`Request type: ${req.method}`)
            next();
        }, login);

}



export default routes;
