<template>
  <div class="password-box">
    <v-text-field
      label="현재 비밀번호"
      v-model="userPw"
      type="number"
      maxlength="6"
      outlined
      class="password-input"
      :rules="[numberOnlyRule]" 
    ></v-text-field>

    <v-text-field
      label="새로운 비밀번호"
      v-model="newPw"
      type="number"
      maxlength="6"
      outlined
      class="password-input"
      :rules="[numberOnlyRule]" 
    ></v-text-field>

    <v-text-field
      label="새로운 비밀번호 확인"
      v-model="confirmPw"
      type="number"
      maxlength="6"
      outlined
      class="password-input"
      :rules="[numberOnlyRule]"
    ></v-text-field>

    <v-btn @click="changePassword" color="lime" rounded class="submit_button">변경하기</v-btn>
  </div>
</template>

<script>
import { ref } from 'vue';
import { $changePw } from '@/api/user'
import { useUserStore } from '@/stores/userStore';

export default {
  setup() {
    const userPw = ref('');
    const newPw = ref('');
    const confirmPw = ref('');

    const numberOnlyRule = (v) => /^[0-9]*$/.test(v) || '숫자만 입력 가능합니다.'; // 숫자만 허용하는 정규식

    const changePassword = async () => {
      // 비밀번호 비교부분
      if (newPw.value == "" || userPw.value == "" || confirmPw.value == "") {
        alert('모든 입력창에 값을 입력해주세요!')
        return;
      }
      if (newPw.value !== confirmPw.value) {
        alert('입력하신 새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.\n다시한번 확인해주세요');
        return;
      }
      if (newPw.value == userPw.value) {
        alert('기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.');
        return;
      }

      try {
        const res = await $changePw(useUserStore().userId, userPw.value, newPw.value)
        if (res.data.message == "Password is wrong") {
          alert("기존비밀번호가 입력하신 비밀번호와 다릅니다 확인해주세요!")
          return;
        }
      } catch (error) {
        alert("비밀번호 번경중 오류가 발생했습니다.\n관리자에게 문의해주세요")
      }
      alert("비밀번호 변경에 성공했습니다\n자동 로그아웃됩니다 다시 로그인 해주세요")
    }

    return {
      userPw,
      newPw,
      confirmPw,
      numberOnlyRule,
      changePassword
    }
  }
};
</script>

<style scoped>
.password-box {
  padding: 50px;
  margin: 120px auto;
  border: 1px solid #e0e0e0;
  border-radius: 16px;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  height: 400px;
  width: 900px;
}

.password-input {
  margin-bottom: 11px; 
  height: 64px; /* 높이 */
  width: 77%;   /* 너비 */
  margin-left: auto; /* 중앙정렬 */
  margin-right: auto; /* 중앙정렬 */
}

.submit_button {
  margin: 11px;
  float: right;
}
</style>