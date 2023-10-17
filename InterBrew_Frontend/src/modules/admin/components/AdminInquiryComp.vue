<template>
  <br>
    <v-container fluid>
      <v-row justify="center">
        <v-col cols="auto">
          <v-btn class="mr-4" size="large" color="red" variant="tonal" @click="() => getInquiryByStatus('Y')">미처리</v-btn>
          <v-btn size="large" color="blue" variant="tonal" @click="() => getInquiryByStatus('N')">처리완료</v-btn>
        </v-col>
      </v-row>
    </v-container>
    <v-table>
      <thead>
        <tr>
          <th class="text-left">
            아이디
          </th>
          <th class="text-left">
            요청내역
          </th>
          <th class="text-left">
            요청날짜
          </th>
          <th class="text-left">
            처리상태
          </th>
          <th class="text-left">
            처리완료하기
          </th>

        </tr>
      </thead>
      <tbody>
        <tr
          v-for="inquiry in inquiryList"
          :key="inquiry.inquiryNo"
        >
          <td>{{ inquiry.userId }}</td>
          <td>{{ inquiry.inquiryContents }}</td>
          <td>{{ inquiry.inquiryCreatedAt }}</td>
          <td>
            <span v-if="inquiry.inquiryStatus === 'Y'" :style="{ color: 'red' }">미처리</span>
            <span v-else-if="inquiry.inquiryStatus === 'N'" :style="{ color: 'blue' }">처리완료</span>
          </td>
          <td v-if="inquiry.inquiryStatus === 'Y'">
            <v-btn variant="tonal" @click="() => updateInquiryStatus(inquiry.inquiryNo)">처리 완료</v-btn>
          </td>
          <td v-else></td>
        </tr>
      </tbody>
    </v-table>
    <v-pagination v-model="currentPage" :length="totalPages"></v-pagination>
  </template>
  
  <script>
  import { ref, onMounted, computed } from 'vue';
  import { useInquiryStore  } from "@/stores/inquiryStore";
  import { $getInquiryByStatus, $getAllInquiries, $updateInquiryStatus } from "@/api/inquiry"

  export default {
    name: 'AdminInquiryComp',
    setup() {

      const inquiryStore = useInquiryStore();
      const inquiries = ref([]);
      const inquiriesPerPage = 10;
      const currentPage = ref(1);


      // 관리자 전체 요구사항 조회
      async function getAllInquiries() {
        try {
          const res = await $getAllInquiries();
          if (res.data != null || res.data != '') {
            inquiryStore.setInquiries(res.data); 
            console.log(res.data);
            inquiries.value = res.data;
          }
        } catch (err) {
          console.log(err);
        }
      }

     
      // 관리자 요구사항 상태별 조회 : 처리/미처리
      async function getInquiryByStatus(status){
        try {
          currentPage.value = 1;
          const res = await $getInquiryByStatus(status);
          if(res.data != null || res.data != '') {
            console.log(res.data);
            inquiries.value = res.data;
          } 
        } catch (err) {
          console.log(err);
        }

      }

       // 관리자 요구사항 상태 수정 : 완료 처리
       async function updateInquiryStatus(inquiryNo){
        try{
          await $updateInquiryStatus(inquiryNo)
          getAllInquiries()
        } catch (err) {
          console.log(err)
        }
      }

      // 조회 결과 페이지네이션
      const inquiryList = computed(() => {
        const start = (currentPage.value - 1) * inquiriesPerPage;
        const end = start + inquiriesPerPage;
        return inquiries.value.slice(start, end);
      });

      const totalPages = computed(() => Math.ceil(inquiries.value.length / inquiriesPerPage));

      onMounted(getAllInquiries);

      return {
        inquiryList,
        currentPage,
        totalPages,
        getInquiryByStatus,
        updateInquiryStatus
      };
    }
  };
</script>

