<template>
    <admin-header-comp></admin-header-comp>
    <router-view></router-view>
</template>

<script>
import AdminHeaderComp from '@/modules/admin/components/AdminHeaderComp.vue';

export default {
    name:'AdminLayout',
    components: {
        AdminHeaderComp
    }
}
</script>

<style>

</style>