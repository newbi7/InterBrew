<template>
    <br>
    <div class="text-center">
      <v-menu>
        <template v-slot:activator="{ props: menu }">
          <v-tooltip location="top">
            <template v-slot:activator="{ props: tooltip }">
              <v-btn
                size = "large"
                color = "primary"
                v-bind = "mergeProps(menu, tooltip)"
              >
                   {{ selectedDate ? selectedDate : '날짜' }}   
              </v-btn>
            </template>
            <span> 날짜를 선택해주세요! </span>
          </v-tooltip>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, index) in items"
            :key="index"
            @click="getSumByDateAndUser(item.title)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
    <br>
    <v-table>
      <thead>
        <tr>
          <th class="text-left">결제날짜</th>
          <th class="text-left">아이디</th>
          <th class="text-left">결제 총 금액</th>
          <th class="text-left">결제 총 수량</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="payment in paymentList" :key="payment.userId">
          <td>{{ payment.paymentCreatedAt }}</td>
          <td>{{ payment.userId }}</td>
          <td>{{ payment.paymentTotalPrice }}원</td>
          <td>{{ payment.paymentTotalCount }}개</td>
        </tr>
      </tbody>
    </v-table>
    <v-pagination v-model="currentPage" :length="totalPages"></v-pagination>
  </template>
  
  <script>
  import { ref, onMounted, computed, mergeProps } from 'vue'
  import { $getSumByDateAndUser } from '@/api/payment';
  
  export default {
    name: "AdminPaymentTotalComp",
    setup() {
  
      // 날짜 관련
      const currentDate = new Date(); 
      const currentYear = currentDate.getFullYear(); 
      const currentMonth = String(currentDate.getMonth() + 1).padStart(2, '0');  
      
      const selectedDate = ref(`${currentYear}-${currentMonth}`); 
      
      const items = computed(() => generateDateItems());
  
      // 조회 데이터 페이지네이션 관련
      const payments = ref([]);
      const paymentsPerpage = 10;
      const currentPage = ref(1);
  
      // 관리자 - 전체 회원 결제 내열 월별 조회
      async function getSumByDateAndUser(date){
        selectedDate.value = date;
        const formattedMonth = formatDateToFullString(date);
        console.log(formattedMonth);
        try {
            const res = await $getSumByDateAndUser(formattedMonth);
            if (res.data != null || res.data != '') {
              console.log(res.data);
              payments.value = res.data;
            }
          } catch (err) {
            console.log(err);
          }
      }
  
      // 조회 결과 페이지네이션
      const paymentList = computed(() => {
          const start = (currentPage.value - 1) * paymentsPerpage;
          const end = start + paymentsPerpage;
          return payments.value.slice(start, end);
        });
  
      const totalPages = computed(() => Math.ceil(payments.value.length / paymentsPerpage));
  
      onMounted(() => { getSumByDateAndUser(selectedDate.value);});
  
      return {
        selectedDate,
        paymentList,
        currentPage,
        totalPages,
        items,
        mergeProps,
        getSumByDateAndUser
      };
    }
  }
  
  
  // 날짜 형식 변환
  function formatDateToFullString(dateString) {
    return `${dateString}-01 00:00:00`;
  }
  
   // drawer에 담길 날짜 생성
  function generateDateItems() {
    let startYear = 2020;
    let endYear = 2026;
    let items = [];
  
    for (let year = startYear; year <= endYear; year++) {
      for (let month = 1; month <= 12; month++) {
        let formattedMonth = month < 10 ? `0${month}` : `${month}`;
        items.push({ title: `${year}-${formattedMonth}` });
      }
    }
  
    return items;
  }
  </script>
  