<template>
  <v-container>

  </v-container>
</template>

<script>
export default {
  created() {
    const token = this.$route.query.token;
    console.log('token', token);

    if(token) {
      this.$store.commit('setToken', token);
    }

    this.$router.replace('/room-list')
  }
}
</script>

<style>

</style>