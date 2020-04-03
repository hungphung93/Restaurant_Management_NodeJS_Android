import express from 'express';
import mongoose from 'mongoose';
import bodyParser from 'body-parser';
import cors from 'cors';
import authRoutes from './src/routes/authRoutes';
import tableRoutes from './src/routes/tableRoutes';
import foodRoutes from './src/routes/foodRoutes';
import config from './config.json';
import { logger } from './src/shared/utilities/LoggingUtilities'
import * as jwtUtilities from './src/shared/utilities/jsonWebTokenUtilities';

const app = express();

// mongoose connection
mongoose.Promise = global.Promise;

mongoose.connect(`mongodb://${config.db.host}:${config.db.port}/${config.db.name}`, {
    useMongoClient: config.db.useMongoClient
});


// bodyparser setup
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


// serving static files
app.use(express.static('public'));

app.use(cors({ origin: true }));

app.all('*', (req, res, err, next) => {
    if (err) {
        logger.error(req, err);
    } else {
        res.setHeader('Access-Control-Allow-Origin', '*');
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE'); // If needed
        res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type'); // If needed
        res.setHeader('Access-Control-Allow-Credentials', true); // If needed
    }

    next();
});
app.use('/images', express.static(__dirname + '/images'));

// Register JWT Authentication
app.use(async (req, res, next) => {
    try {
        if (req.headers && req.headers.authorization && req.headers.authorization.split(' ')[0] === 'JWT') {
            let authorization = req.headers.authorization;
            let accessToken = authorization.split(' ')[1];

            let userData = await jwtUtilities.verifyToken(accessToken);

            console.log(userData);

            req.userInfo = userData;
            next();

        } else {
            req.userInfo = undefined;
            next();
        }
    } catch (err) {
        logger.error(err);
        console.log(err);
        req.userInfo = undefined;
        next();
    }

});


// Register api Routing
authRoutes(app);
tableRoutes(app);
foodRoutes(app);

app.get('/', (req, res) =>
    res.send(`Node and express server is running on port ${config.app.port}`)
);

app.listen(config.app.port, () =>
    console.log(`your server is running on port ${config.app.port}`)
);