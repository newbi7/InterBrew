<template>
    <mypage-header-comp></mypage-header-comp>
    <router-view class="router-view-spacing"></router-view>
</template>

<script>
import MypageHeaderComp from '@/modules/mypage/components/MypageHeaderComp.vue';

export default {
    name:'MypageLayout',
    components: {
        MypageHeaderComp
    }
}
</script>

<style scoped>
.router-view-spacing {
    margin-top: 20px; /* 원하는 간격으로 조정 */
}
</style>