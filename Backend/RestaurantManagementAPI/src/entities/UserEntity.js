import mongoose from 'mongoose';
import * as databaseUtilities from '../shared/utilities/databaseUtilities';
import * as mongoDBEntityNames from '../shared/constants/mongoDBEntityNames';
import * as encryptUtilities from '../shared/utilities/encryptUtilities';

const UserSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    role: {
        type: String,
        required: true
    },
    created_date: {
        type: Date,
        default: Date.now
    }
});

UserSchema.methods.comparePassword = (password, hashedPassword) => {
    return encryptUtilities.compare(password, hashedPassword);
};

export const UserEntity = databaseUtilities.getEntity(mongoDBEntityNames.userCollection, UserSchema);
