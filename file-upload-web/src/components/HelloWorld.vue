<template>
  <div class="content">
    <el-upload
      class="upload-demo"
      action="file/upload"
      :on-remove="handleRemove"
      multiple
      :limit="3"
      :on-exceed="handleExceed"
      :on-success="onSuccess"
      :on-preview="handlePreview"
      :file-list="fileList">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
    <el-row style="margin-top: 20px">
      <el-button type="primary" size="small" @click="queryFiles">请求测试</el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      fileList: []
    }
  },
  methods: {
    onSuccess() {
      this.$message({
        message: '上传成功',
        type: 'success'
      })
      this.queryFiles()
    },
    beforeUpload() {

    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，
      共选择了 ${files.length + fileList.length} 个文件`)
    },
    handleRemove(file, fileList) {
      let attachId = file.attach_id
      this.$axios.post('file/deleteAttach?attachId=' + attachId).then((res) => {
        if (res.status === 200) {
          this.$message({
            message: res.data,
            type: 'success'
          })
        } else {
          this.$message({
            message: res.data,
            type: 'error'
          })
        }
      })
    },
    queryFiles() {
      this.fileList = []
      this.$axios.post('file/findAll').then((res) => {
        let files = JSON.parse(JSON.stringify(res))
        files.data.forEach(item => {
          let file = {
            attach_id: item.attachId,
            attachName: item.attachName,
            attachType: item.attachType,
            savePath: item.savePath,
            url: item.savePath,
            name: item.attachName
          }
          this.fileList.push(file)
        })
      })
    },
    handlePreview(file) {
      var a = document.createElement('a')
      var event = new MouseEvent('click')
      a.download = file.name
      a.href = file.url
      a.dispatchEvent(event)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.content {
  width: 30%;
  height: auto;
  margin: auto;
  padding: 20px;
  border: 2px solid lightgrey;

}
</style>
