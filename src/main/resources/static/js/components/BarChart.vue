<template>
  <v-row justify="center" align="center">
    <v-col cols="6" align="center" v-if="loading">
      <v-progress-circular class="transition-ease-in-out" indeterminate :color="colorOfLoading" size="50" width="3">
      </v-progress-circular>
    </v-col>
    <v-col v-else cols="6" align="center">
      <div>
        <canvas ref="canvas" width="500" height="500" id="canvas"></canvas>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import {PolarArea} from 'vue-chartjs'
import Webquizservice from "../service/webquizservice";

export default {
  extends: PolarArea,
  data() {
    return {
      loading: true,
      colorOfLoading: 'primary',
      sizeQuizzesInDayData: [],
      quizzesLabels: new Set([]),
      months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],

    }
  },
  methods: {
    random_rgba() {
      var o = Math.round, r = Math.random, s = 255;
      return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',' + r().toFixed(1) + ')';
    },

    changeColorOfLoading() {
      var randomColor = Math.floor(Math.random() * 16777215).toString(16);
      this.colorOfLoading = '#' + randomColor;
    },
    async setup() {
      await Webquizservice.getQuizzesIn30Days()
          .then((response) => {
            let day = null;
            let sizeQuizzesOfDay = 0;
            response.data.forEach((el, idx, array) => {
              let date = new Date(el.date);
              if (day == null || day === date.getDate()) {
                sizeQuizzesOfDay++;
                day = date.getDate();
              } else {
                this.sizeQuizzesInDayData.push(sizeQuizzesOfDay);
                sizeQuizzesOfDay = 1;
                day = date.getDate();
              }
              if (idx === array.length - 1) {
                this.sizeQuizzesInDayData.push(sizeQuizzesOfDay);
                sizeQuizzesOfDay = 1;
                day = date.getDate();
              }
              this.quizzesLabels.add(date.getDate() + " " + this.months[date.getMonth()]);
            })
          })
          .catch((error) => {
            //catching
            console.log(error);
          })
          .finally(() => this.loading = false);
      let data1 = {
        labels: Array.from(this.quizzesLabels),
        datasets: [{
          label: 'Size of created quizzes per day in month ',

          data: this.sizeQuizzesInDayData,
          backgroundColor: [
            'rgba(255, 99, 132, 0.4)',
            'rgba(54, 162, 235, 0.4)',
            'rgba(255, 206, 86, 0.4)',
            'rgba(75, 192, 192, 0.4)',
            'rgba(153, 102, 255, 0.4)',
            'rgba(255, 159, 64, 0.4)',
            'rgba(157,0,0, 0.4)',
            'rgba(108,134,255,0.4)',
            'rgba(119,255,34,0.4)',
            'rgba(0,255,179,0.4)',
            'rgba(255,0,143,0.4)',
            'rgba(255,219,72,0.4)',
            'rgba(87,10,105,0.4)',
            'rgba(195,255,0,0.4)',
            'rgba(255,0,0,0.4)',
            'rgba(0,119,255,0.4)',
            'rgba(233,96,238,0.4)',
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(157,0,0, 1)',
            'rgba(108,134,255,1)',
            'rgba(119,255,34,1)',
            'rgba(0,255,179,1)',
            'rgba(255,0,143,1)',
            'rgba(255,219,72,1)',
            'rgba(87,10,105,1)',
            'rgba(195,255,0,1)',
            'rgba(255,0,0,1)',
            'rgba(0,119,255,1)',
            'rgba(233,96,238,1)',
          ],
          borderRadius: 3.0,
          borderWidth: 1
        }]
      };
      let options = {
        scales: {
          yAxes: [{
            display: true,
            ticks: {
              beginAtZero: true
            }
          }]
        }
      };
      this.renderChart(data1, options);


    },
  },
  mounted() {
    setInterval(this.changeColorOfLoading, 3500);
    this.setup();
  },
}
</script>

<style>
.small {
  max-width: 600px;
  margin: 150px auto;
}
</style>