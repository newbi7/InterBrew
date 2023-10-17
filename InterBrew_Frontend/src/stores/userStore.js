import { defineStore } from 'pinia';

export const useUserStore = defineStore({
  id : 'user', 
  persist : {
    Storage : sessionStorage
  },
  state: () => ({
    userNo: '',
    userId: '',
    userName: '',
    userToken: '',
    fetchCount: '0',
  }),
  getters: {
    getUserId() {
      return this.userId;
    },
    getUserToken() {
      return this.userToken;
    }
  },
  actions: {
    setUserNo(userNo) {
      this.userNo = userNo;
    },
    setUserId(userId) {
      this.userId = userId;
    },
    setUserName(userName) {
      this.userName = userName;
    },
    setUserToken(userToken) {
      this.userToken = userToken;
    },
    async fetchUserToken(){
      if (!this.userToken && this.fetchCount < 5) {
        await new Promise(resolve => setTimeout(resolve, 1000)); 
        this.fetchCount++;
        await this.fetchUserToken(); 
      }
    },
    clearUser() {
      this.userId = '';
      this.userName = '';
      this.userNo = '';
      this.userToken = '';
    },
  },
});
