import { createRouter, createWebHistory } from "vue-router";
import loginView from "@/modules/login/views/LoginLayout.vue";
import paymentView from "@/modules/payment/views/PaymentLayout.vue";
import paycheckView from "@/modules/paycheck/views/PaycheckLayout.vue";
import AdminLayout from "@/modules/admin/views/AdminLayout.vue";
import AdminPaymentLayout from "@/modules/admin/views/AdminPaymentLayout.vue";
import AdminStatisticsView from "@/modules/admin/views/AdminStatisticsView.vue";
import AdminItemView from "@/modules/admin/views/AdminItemView.vue";
import AdminPaymentDetailView from "@/modules/admin/views/AdminPaymentDetailView.vue";
import AdminPaymentTotalView from "@/modules/admin/views/AdminPaymentTotalView.vue";
import AdminInquiryView from "@/modules/admin/views/AdminInquiryView.vue";
import MypageLayout from "@/modules/mypage/views/MypageLayout";
import MypageStatisticsView from "@/modules/mypage/views/MypageStatisticsView";
import MypageInquiryView from "@/modules/mypage/views/MypageInquiryView";
import MyPageChangePwView from "@/modules/mypage/views/MypageChangePwView";
import { useUserStore } from "@/stores/userStore";

const routes = [
  {
    path: "/",
    component: loginView,
  },
  {
    path: "/payment",
    component: paymentView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/paycheck",
    name: "paycheck",
    component: paycheckView,
    props: true,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/admin/main",
    name: "AdminLayout",
    component: AdminLayout,
    meta: {
      requiresAuth: true,
    },
    children: [
      {
        path: "/admin/main",
        name: "AdminStatisticsView",
        component: AdminStatisticsView,
      },
      {
        path: "/admin/item",
        name: "AdminItemView",
        component: AdminItemView,
      },
      {
        path: "/admin/payment/detail",
        name: "AdminPaymentLayout",
        component: AdminPaymentLayout,
        children: [
          {
            path: "/admin/payment/detail",
            name: "AdminPaymentDetailView",
            component: AdminPaymentDetailView,
          },
          {
            path: "/admin/payment/total",
            name: "AdminPaymentTotalView",
            component: AdminPaymentTotalView,
          },
        ],
      },
      {
        path: "/admin/inquiry",
        name: "AdminInquiryView",
        component: AdminInquiryView,
      },
    ],
  },
  {
    path: "/mypage",
    name: "mypageLayout",
    component: MypageLayout,
    meta: {
      requiresAuth: true,
    },
    children: [
      {
        path: "",
        name: "mypageStatisticsView",
        component: MypageStatisticsView,
      },
      {
        path: "inquiry",
        name: "mypageInquiryView",
        component: MypageInquiryView,
      },
      {
        path: "changePw",
        name: "mypageChangePwView",
        component: MyPageChangePwView,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  const userId = userStore.userId;
  try {
    if (!userStore.userToken) {
      await userStore.fetchUserToken();
    }
    if (to.path === "/") {
      if (userStore.userToken) {
        next();
      } else {
        next();
      }
    } else if (to.matched.some((record) => record.meta.requiresAuth)) {
      if (!userStore.userToken) {
        alert("로그인을 해주세요");
        next("/");
      } else if (!userId.includes("admin") && to.path.startsWith("/admin")) {
        alert("관리자만 접근할 수 있는 페이지입니다.");
        next("/");
      } else {
        next();
      }
    } else {
      next();
    }
  } catch (error) {
    console.error("Error fetching user token:", error);
    next("/");
  }
});

export default router;
