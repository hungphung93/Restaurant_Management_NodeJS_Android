import mongoose from 'mongoose';
import * as databaseUtilities from '../shared/utilities/databaseUtilities';
import * as mongoDBEntityNames from '../shared/constants/mongoDBEntityNames';
import { FoodStatus } from '../shared/enum/FoodStatus';

export class OrderedFood {
    constructor(food_id, status, quantity, price) {
        this.food_id = food_id;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
    }
}

/*
const OrderedFoodSchema = new mongoose.Schema({
    food_id: {
        type: String,
        required: true
    },
    status: {
        type: String,
        enum: [FoodStatus.ONLINE, FoodStatus.CANCELED, FoodStatus.COOKING, FoodStatus.READY, FoodStatus.SERVED],
        default: FoodStatus.ONLINE
    },
    quantity: {
        type: Number,
        required: true
    },
    price: {
        type: Number,
        required: true
    }
});

export const OrderedFoodEntity = databaseUtilities.getEntity(mongoDBEntityNames.orderedFoodCollection, OrderedFoodSchema);
*/
