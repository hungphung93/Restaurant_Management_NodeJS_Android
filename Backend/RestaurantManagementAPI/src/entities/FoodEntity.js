import mongoose from 'mongoose';
import * as databaseUtilities from '../shared/utilities/databaseUtilities';
import * as mongoDBEntityNames from '../shared/constants/mongoDBEntityNames';

const FoodSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    image_url: {
        type: String,
        required: true
    },
    category: {
        type: String,
        required: true
    },
    price: {
        type: Number,
        required: true
    },
    is_deleted: {
        type: Boolean,
        default: false
    },
    deleted_at: {
        type: Date,
        default: null
    }
});

export const FoodEntity = databaseUtilities.getEntity(mongoDBEntityNames.foodCollection, FoodSchema);
