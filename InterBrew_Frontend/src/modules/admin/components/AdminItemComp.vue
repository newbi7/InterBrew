<template>
  <br />
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="auto">
        <v-btn
          size="large"
          color="amber-accent-4"
          variant="tonal"
          @click="addButton"
          >새로운 품목 추가</v-btn
        >
      </v-col>
    </v-row>
  </v-container>
  <v-table>
    <thead>
      <tr>
        <th class="text-left">품목명</th>
        <th class="text-left">품목이미지</th>
        <th class="text-left">품목수량</th>
        <th class="text-left">입고일자</th>
        <th class="text-left">유통기한</th>
        <th class="text-left">가격</th>
        <th class="text-left">공급상태</th>
        <th class="text-left">정보수정</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="item in coffeeList" :key="item.coffeeName">
        <td>{{ item.coffeeName }}</td>
        <img
          :src="item.coffeeImage"
          alt="품목 이미지"
          width="100"
          height="100"
        />
        <td :class="{ 'red-text': item.coffeeCount <= 5 }">
          {{ item.coffeeCount }}
          <span v-if="item.coffeeCount <= 5" class="red-text"
            >(!재고부족!)</span
          >
        </td>
        <td>{{ item.coffeeCreatedAt }}</td>
        <td>{{ item.coffeeExpireDate }}</td>
        <td>{{ item.coffeePrice }}</td>
        <td>{{ item.coffeeStatus === "Y" ? "공급 중" : "공급 불가" }}</td>
        <td>
          <v-btn variant="tonal" @click="showEditOptions(item)">수정</v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>

  <v-dialog v-model="dialogVisible" max-width="500px">
    <v-card>
      <v-card-title>새로운 품목 추가</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="addNewCoffeeItem">
          <v-text-field
            v-model="newCoffeeItem.coffeeName"
            label="품목명"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-text-field>
          <v-text-field
            v-model="newCoffeeItem.coffeeInformation"
            label="품목정보"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-text-field>
          <v-text-field
            v-model="newCoffeeItem.coffeeCount"
            label="품목수량"
            type="number"
            :rules="[
              (value) => !!value || '값을 입력하세요',
              (value) => value <= 10000 || '최대값은 10000입니다',
            ]"
          ></v-text-field>
          <div>
            입고일자
            <VueDatePicker
              v-model="newCoffeeItem.coffeeCreatedAt"
              :format="formatDateString"
              :rules="[(value) => !!value || '값을 입력하세요']"
            ></VueDatePicker>
          </div>
          <br />
          <div>
            유통기한
            <VueDatePicker
              v-model="newCoffeeItem.coffeeExpireDate"
              :format="formatDateString"
              :rules="[(value) => !!value || '값을 입력하세요']"
            ></VueDatePicker>
          </div>
          <br />
          <v-file-input
            label="이미지 업로드"
            accept="image/*"
            prepend-icon="mdi-camera"
            enctype="multipart/form-data"
            @change="handleImageUpload"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-file-input>
          <v-text-field
            v-model="newCoffeeItem.coffeePrice"
            label="가격"
            type="number"
            :rules="[
              (value) => !!value || '값을 입력하세요',
              (value) => value <= 10000 || '최대값은 10000입니다',
            ]"
          ></v-text-field>
          <v-select
            v-model="newCoffeeItem.coffeeStatus"
            :items="['Y', 'N']"
            label="공급상태"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-select>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color="red darken-1" @click="dialogVisible = false">취소</v-btn>
        <v-btn type="submit" color="green" @click="addNewCoffeeItem"
          >추가</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-dialog v-model="modifyDialogVisible" max-width="500px">
    <v-card>
      <v-card-title>품목 수정</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="modifingCoffeeItem">
          <v-text-field
            v-model="modifyCoffeeItem.coffeeName"
            label="품목명"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-text-field>
          <v-text-field
            v-model="modifyCoffeeItem.coffeeInformation"
            label="품목정보"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-text-field>
          <v-text-field
            v-model="modifyCoffeeItem.coffeeCount"
            label="품목수량"
            type="number"
            :rules="[
              (value) => !!value || '값을 입력하세요',
              (value) => value <= 10000 || '최대값은 10000입니다',
            ]"
          ></v-text-field>
          <div>
            입고일자
            <VueDatePicker
              v-model="modifyCoffeeItem.coffeeCreatedAt"
              :format="formatDateString"
              :rules="[(value) => !!value || '값을 입력하세요']"
            ></VueDatePicker>
          </div>
          <br />
          <div>
            유통기한
            <VueDatePicker
              v-model="modifyCoffeeItem.coffeeExpireDate"
              :format="formatDateString"
              :rules="[(value) => !!value || '값을 입력하세요']"
            ></VueDatePicker>
          </div>
          <br />
          <v-text-field
            v-model="modifyCoffeeItem.coffeePrice"
            label="가격"
            type="number"
            :rules="[
              (value) => !!value || '값을 입력하세요',
              (value) => value <= 10000 || '최대값은 10000입니다',
            ]"
          ></v-text-field>
          <v-select
            v-model="modifyCoffeeItem.coffeeStatus"
            :items="['Y', 'N']"
            label="공급상태"
            :rules="[(value) => !!value || '값을 입력하세요']"
          ></v-select>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color="red darken-1" @click="modifyDialogVisible = false"
          >취소</v-btn
        >
        <v-btn type="submit" color="green" @click="modifingCoffeeItem"
          >수정</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { ref, onMounted } from "vue";
import { useCoffeeStore } from "@/stores/coffeeStore";
import { useUserStore } from "@/stores/userStore";
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { $coffeeAdd, $coffeeList, $coffeeModify } from "@/api/coffee";

export default {
  name: "AdminItemComp",
  components: {
    VueDatePicker,
  },
  setup() {
    const coffeeStore = useCoffeeStore();
    const userStore = useUserStore();
    const coffeeList = ref(coffeeStore.coffee);
    const dialogVisible = ref(false);
    const modifyDialogVisible = ref(false);
    const datePickerField = ref("coffeeCreatedAt");
    const datePickerFormat = "yyyy-MM-dd";
    const stockEditDialogVisible = ref(false);
    const stockDialogVisible = ref(false);
    const selectedCoffeeItem = ref(null); // 수정할 커피 아이템
    const newStockCount = ref(""); // 재고 수정을 위한 새로운 값

    const newCoffeeItem = ref({
      coffeeName: "",
      coffeeInformation: "",
      coffeeCount: "",
      coffeeCreatedAt: "",
      coffeeExpireDate: "",
      coffeePrice: "",
      coffeeStatus: "Y",
    });

    const modifyCoffeeItem = ref({
      coffeeName: "",
      coffeeInformation: "",
      coffeeCount: "",
      coffeeCreatedAt: "",
      coffeeUserUpdate: "",
      coffeeUserCreate: "",
      coffeeImage: "",
      coffeeExpireDate: "",
      coffeePrice: "",
      coffeeStatus: "Y",
    });

    async function addNewCoffeeItem() {
      if (!newCoffeeItem.value.coffeeImage) {
        alert("이미지를 업로드해주세요.");
        return;
      }
      if (newCoffeeItem.value) {
        const newCoffeeImage = new FormData();
        const userId = userStore.userId;
        newCoffeeImage.append("coffeeImage", newCoffeeItem.value.coffeeImage);
        newCoffeeItem.value.coffeeCreatedAt = formatDateString(
          newCoffeeItem.value.coffeeCreatedAt
        );
        newCoffeeItem.value.coffeeExpireDate = formatDateString(
          newCoffeeItem.value.coffeeExpireDate
        );

        const createdDate = new Date(newCoffeeItem.value.coffeeCreatedAt);
        const expireDate = new Date(newCoffeeItem.value.coffeeExpireDate);

        if (createdDate > expireDate) {
          alert("유통기한 날짜는 입고 날짜보다 나중이어야 합니다.");
          return;
        }

        const coffeeAddResponse = await $coffeeAdd(
          newCoffeeItem,
          newCoffeeImage,
          userId
        );

        if (!coffeeAddResponse.data.message) {
          alert("커피 추가 완료");
          dialogVisible.value = false;
          getCoffeeList();
        } else {
          alert(coffeeAddResponse.data.message);
        }
      }
    }

    async function modifingCoffeeItem() {
      if (modifyCoffeeItem.value) {
        modifyCoffeeItem.value.coffeeCreatedAt = formatDateString(
          modifyCoffeeItem.value.coffeeCreatedAt
        );
        modifyCoffeeItem.value.coffeeExpireDate = formatDateString(
          modifyCoffeeItem.value.coffeeExpireDate
        );

        const createdDate = new Date(modifyCoffeeItem.value.coffeeCreatedAt);
        const expireDate = new Date(modifyCoffeeItem.value.coffeeExpireDate);

        if (createdDate > expireDate) {
          alert("유통기한 날짜는 입고 날짜보다 나중이어야 합니다.");
          return;
        }

        const coffeeAddResponse = await $coffeeModify(modifyCoffeeItem);

        if (!coffeeAddResponse.data.message) {
          alert("커피정보 수정 완료");
          modifyDialogVisible.value = false;
          getCoffeeList();
        } else {
          alert(coffeeAddResponse.data.message);
        }
      }
    }

    function formatDateString(dateString) {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      const hours = date.getHours().toString().padStart(2, "0");
      const minutes = date.getMinutes().toString().padStart(2, "0");
      const seconds = date.getSeconds().toString().padStart(2, "0");

      return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    }

    function addButton() {
      dialogVisible.value = true;
    }

    function handleImageUpload(event) {
      newCoffeeItem.value.coffeeImage = event.target.files[0];
    }

    // 커피 정보 조회
    async function getCoffeeList() {
      try {
        const res = await $coffeeList();
        if (res.data != null || res.data != "") {
          coffeeStore.setCoffee(res.data);
          coffeeList.value = res.data;
        }
      } catch (err) {
        console.log(err);
      }
    }

    function showEditOptions(item) {
      selectedCoffeeItem.value = item; // 선택된 아이템값 저장
      modifyCoffeeItem.value = {
        // 선택된 아이템 modifyCoffeeItem 업데이트
        coffeeId: item.coffeeId,
        coffeeName: item.coffeeName,
        coffeeInformation: item.coffeeInformation,
        coffeeCount: item.coffeeCount,
        coffeeCreatedAt: item.coffeeCreatedAt,
        coffeeImage: item.coffeeImage,
        coffeeUserCreate: item.coffeeUserCreate,
        coffeeUserUpdate: userStore.userId,
        coffeeExpireDate: item.coffeeExpireDate,
        coffeePrice: item.coffeePrice,
        coffeeStatus: item.coffeeStatus,
      };
      modifyDialogVisible.value = true; // 수정 다이얼로그를 표시
    }

    onMounted(() => {
      getCoffeeList();
    });

    return {
      coffeeList,
      addButton,
      addNewCoffeeItem,
      modifingCoffeeItem,
      dialogVisible,
      modifyDialogVisible,
      newCoffeeItem,
      modifyCoffeeItem,
      datePickerField,
      VueDatePicker,
      datePickerFormat,
      handleImageUpload,
      formatDateString,
      stockEditDialogVisible,
      selectedCoffeeItem,
      newStockCount,
      showEditOptions,
      stockDialogVisible,
    };
  },
};
</script>

<style scoped>
.custom-dialog-style {
  opacity: 1;
}

.btn-right {
  margin-left: 300px;
}

.red-text {
  color: red;
  background-color: rgb(255, 252, 188);
}
</style>
