import jwt from 'jsonwebtoken';
import config from '../../../config.json';

export const createToken = async (username, userId, role) => {

    try {
        var user = {
            "username": username,
            "_id": userId,
            "role": role
        }

        return await jwt.sign({ user: user }, config.jwt.secretKey);
    } catch (err) {
        throw err;
    }
}

export const verifyToken = async (token) => {
    try {
        let data = await jwt.verify(token, config.jwt.secretKey);
        return await data;
    }
    catch (err) {
        throw 'Invalid access token';
    }
}
