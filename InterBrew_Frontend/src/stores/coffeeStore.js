import { defineStore } from 'pinia';

export const useCoffeeStore = defineStore({
  id: 'coffee',
  persist : {
    Storage : sessionStorage
  },
  state: () => ({
    coffee: [],
  }),
  getters: {
    getCoffee() {
      return this.coffee;
    },
  },
  actions: {
    setCoffee(coffee) {
      this.clearCoffee();
      this.coffee = coffee;
    },
    clearCoffee() {
      this.coffee = [];
    },
  },
});
