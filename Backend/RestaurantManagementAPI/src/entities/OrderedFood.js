export class OrderedFood {
    constructor(order_id, food_id, status, quantity, price, updated_at) {
        this.order_id = order_id;
        this.food_id = food_id;
        this.status = status;
        this.quantity = quantity;
        this.price = price;
        this.updated_at = updated_at;
    }
}
