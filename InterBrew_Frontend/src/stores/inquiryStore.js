import { defineStore } from 'pinia';

export const useInquiryStore  = defineStore({
  id: 'inquiry',
  state: () => ({
    inquiries: [] 
  }),
  actions: {
    setInquiries(inquiries) {
      this.inquiries = inquiries;
    }
  }
});
