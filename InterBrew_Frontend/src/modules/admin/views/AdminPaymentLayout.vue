<template>
    <admin-payment-menu-comp></admin-payment-menu-comp>
    <router-view></router-view>
</template>

<script>
import AdminPaymentMenuComp from '@/modules/admin/components/AdminPaymentMenuComp.vue';

export default {
    name:'AdminPaymentLayout',
    components: {
        AdminPaymentMenuComp
    }
}
</script>

<style>

</style>