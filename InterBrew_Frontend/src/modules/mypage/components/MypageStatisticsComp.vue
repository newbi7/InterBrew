<template>
  <div class="mypagemain-container">
    <select id="year-select" v-model="selectedYear" class="styled-select">
      <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
    </select>
 
    <select id="month-select" v-model="selectedMonth" class="styled-select">
      <option v-for="month in months" :key="month" :value="month">{{ month }}월</option>
    </select>

  <button @click="fetchPayments" class="styled-button">조회</button>
  
  <br><br>
    <table border="1" class="centered-table table">
      <thead>
        <tr>
          <th>결제일자</th>
          <th>커피 종류</th>
          <th>결제 갯수</th>
          <th>결제 금액</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="payment in payments" :key="payment.id">
          <td>{{ payment.paymentCreatedAt }}</td>
          <td>{{ payment.coffeeName }}</td>
          <td>{{ payment.paymentTotalCount }}</td>
          <td>{{ payment.paymentPrice }}원</td>
        </tr>
      </tbody>
    </table>

    <v-dialog v-model="showDialog" persistent max-width="460px">
      <v-card style="height: 160px; border-radius: 100;">
        <v-card-text class="text-center large-text" style="flex-grow: 1; display: flex; align-items: center; justify-content: center;">
          해당 달의 결제 내역이 없습니다.
        </v-card-text>
        <v-card-actions class="justify-center text-center large-text">
          <v-btn @click="closeDialog">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue';
import { $getMyPaymentListByDate } from '@/api/payment';
import { useUserStore } from '@/stores/userStore';

export default {
  setup() {
    const years = Array.from({ length: 10 }, (_, i) => new Date().getFullYear() - i);
    const months = Array.from({ length: 12 }, (_, i) => i + 1);
    const selectedYear = ref(new Date().getFullYear());
    const selectedMonth = ref(new Date().getMonth() + 1);
    const payments = ref([]);
    const showDialog = ref(false);

    async function fetchPayments() {
      try {
        const selectedDate = `${selectedYear.value}-${selectedMonth.value.toString().padStart(2, '0')}-01 00:00:00`;
        const res = await $getMyPaymentListByDate(selectedDate, useUserStore().userNo);

        if (res.data == '') {
          showDialog.value = true;
        } else if (res && res.data) {
          payments.value = res.data;
        } else {
          alert("결제 내역 불러오기에 실패했습니다. 관리자에게 문의해주세요!");
        }
      } catch (err) {
        console.log(err);
      }
    }

    function closeDialog() {
      showDialog.value = false;
    }
    // 페이지 로드시 현재 달의 데이터를 조회
    onMounted(fetchPayments);
    
    // 선택된 년도나 월이 변경될 때 데이터를 조회
    watch([selectedYear, selectedMonth], fetchPayments);

    return {
      years,
      months,
      selectedYear,
      selectedMonth,
      payments,
      showDialog,
      closeDialog
    };
  },
};
</script>

<style scoped>.centered-table {
  margin-left: auto;
  margin-right: auto;
}

.table {
  border-collapse: collapse;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.table th, .table td {
  padding: 10px 16px;
  font-size: 24px;
  background-color: #f3f4f6;
}

.table th {
  background-color: #e0e4e8;
  border-bottom: 2px solid #ffffff;
}

.table tr:hover {
  background-color: #f2f2f2;
}

.mypagemain-container {
  font-size: 160%;
  text-align: center;
}


.styled-select {
  font-weight: 700;
  background: #FFF5E5;
  border: 1px solid #fffefe;
  border-radius: 16px;
  padding: 10px;
  font-size: 24px;
  margin-right: 20px;  /* 공백 추가 */
}

.styled-label {
  margin: 0 20px;  /* 년과 월 사이의 공백 */
}

.styled-button {
  font-weight: 770;
  background-color: #a3e091;  /* 버튼 색 */
  border: none;
  color: rgb(9, 9, 9);
  padding: 10px 16px;  /* 버튼 안의 패딩 */
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 24px;
  margin-top: 20px;  /* 버튼과의 공백 */
  cursor: pointer;
  border-radius: 16px;  /* 둥근 모서리 */
}
</style>