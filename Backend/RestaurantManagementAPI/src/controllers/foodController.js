import * as foodServices from '../services/foodServices';
import { logger } from '../shared/utilities/loggingUtilities';
import HttpResponseResult from '../shared/models/HttpResponseResult';
import { Food } from '../models/Food';

export const getAllFoods = async (req, res) => {
    try {

        let lstFoods = await foodServices.getAllFoods();

        return await res.status(200).send(new HttpResponseResult(true, "", lstFoods));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}

export const addNewFood = async (req, res) => {
    try {
        let data = req.body;

        console.log(data);

        let food = new Food(null, data.foodName, data.foodDescription,
            data.imageURL, data.category,
            data.price, null, null);

        var createdFood = await foodServices.addNewFood(food);

        return await res.status(200).send(new HttpResponseResult(true, "", createdFood));

    } catch (err) {
        logger.error(err);
        return await res.status(err.code | 400).send(new HttpResponseResult(false, err, null));
    }
}