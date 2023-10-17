<template>
  <div class="inquiry-box">
    <v-textarea
      label="건의사항"
      v-model="inquiryContents"
      outlined
      class="inquiry-input"
      :counter="1000"
    ></v-textarea>
    <p>{{ inquiryContents.length }}/1000</p>  <!-- 현재 입력된 문자 수를 표시 -->
    <v-btn @click="sendInquiry" color="lime" rounded class="submit_button">보내기</v-btn>
  </div>

  <v-dialog v-model="showDialog" persistent max-width="460px">
    <v-card>
      <v-card-text class="text-center">
        <img :src="require('@/assets/images/Inquiry/catimg.gif')" alt="catimg">
        건의사항을 보내주셔서 감사합니다<br>
      </v-card-text>
      <v-card-actions class="justify-center text-center">
        <v-btn @click="closeDialog">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { ref } from 'vue';
import { $createInquiry } from '@/api/inquiry'
import { useUserStore } from '@/stores/userStore';

export default {
  setup() {
    const inquiryContents = ref('');
    const showDialog = ref(false);

    const sendInquiry = async () => {
      if (!inquiryContents.value) {
        alert('건의사항을 입력해주세요.');
        return;
      }
      console.log(useUserStore().userId)
      try {
        const res = await $createInquiry(inquiryContents.value, useUserStore().userNo, useUserStore().userId)
        console.log(res);
        inquiryContents.value = ''; // 텍스트 초기화
        showDialog.value = true;
      } catch (error) {
        console.error('Error:', error);
        alert('죄송합니다 건의사항 요청중 오류가 발생했습니다.\n관리자에게 문의해주세요!');
      }
    }

    const closeDialog = () => {
      showDialog.value = false;
    };

    return {
      inquiryContents,
      sendInquiry,
      showDialog,
      closeDialog,
    };
  }
};
</script>

<style scoped>
.inquiry-box {
  padding: 50px;
  margin: 100px auto; /* 헤더, 푸터 */
  border: 1px solid #e0e0e0; /* 박스 테두리 */
  border-radius: 16px; /* 둥근 모서리 */
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  background-color: #fff; /* 흰색 배경 */
  border-radius: 20px;
  padding-left: 20px;
  padding-right: 20px;
  max-width: 1000px; /* 원하는 너비로 설정 */
  height: 460px;
}

.inquiry-input {
  margin-bottom: 0px; /* 텍스트, 버튼 사이 간격 */
  border-radius: 12px;
  padding-left: 20px;
  padding-right: 20px;
  height: 300px;
}

.submit_button {
  float: right; /* 버튼을 오른쪽으로 정렬 */
  border-radius: 12px;
}
</style>