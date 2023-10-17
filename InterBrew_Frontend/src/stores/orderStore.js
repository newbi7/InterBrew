import { defineStore } from 'pinia';

export const useOrderStore = defineStore({
  id: 'order',
  persist : {
    Storage : sessionStorage
  },
  state: () => ({
    orderedItems: [],
    totalAmount: 0,
  }),
  getters: {
    getTotalAmount() {
      return this.totalAmount;
    },
    getOrderedItems() {
      return this.orderedItems;
    },
  },
  actions: {
    setOrderedItems(items) {
      this.orderedItems = items;
    },
    setTotalAmount(amount) {
      this.totalAmount = amount;
    },
    clearOrder() {
      this.orderedItems = [];
      this.totalAmount = 0;
    },
    clearAll() {
      this.orderedItems = [];
      this.totalAmount = 0;
    },
  },
});
