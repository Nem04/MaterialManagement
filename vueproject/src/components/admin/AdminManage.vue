<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入名字" suffix-icon="el-icon-search" style="width: 200px"
                @keyup.enter.native="loadPost"
      ></el-input>
      <el-select v-model="role"  filterable placeholder="请选择" style="margin-left: 5px" @change="change">
        <el-option
            v-for="item in roles"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#f2f5fc',color:'#555'}"
              border
    >
      <el-table-column prop="id" label="ID" width="150">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="200">
      </el-table-column>
      <el-table-column prop="account" label="账号" width="200">
      </el-table-column>
      <el-table-column prop="password" label="密码" width="200">
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="200">
      </el-table-column>
      <el-table-column prop="role" label="角色" width="200">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.role === 0 ? 'primary' : 'success'"
              disable-transitions>{{scope.row.role === 0? '管理员':'普通用户'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="success" size="small" @click="update(scope.row)" >编辑</el-button>
          <el-popconfirm
              title="确定删除吗"
              @confirm="del(scope.row.id)"
              style="margin-left: 5px"
          >
            <el-button slot="reference" type="danger" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-col :span="20">
            <el-input v-model="form.account"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="身份" prop="role">
          <el-radio-group v-model="form.role">
            <el-radio label="0">普通用户</el-radio>
            <el-radio label="1">管理员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AdminManage",
  data() {
    let checkDuplicate =(rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?account="+this.form.account).then(res=>res.data).then(res=>{
        if(res.code!=200){
          callback()
        }else{
          callback(new Error('账号已经存在'));
        }
      })
    };

    return {
      tableData: [],
      pageSize:2,
      pageNum:1,
      total:0,
      name:'',
      sex:'',
      roles:[
        {
          value:'0',
          label:'超级管理员'
        },
        {
          value:'1',
          label:'普通用户'
        }
      ],
      centerDialogVisible:false,
      form:{
        id:'',
        name:'',
        password:'',
        account:'',
        phone:'',
        role:'1'
      },
      rules:{
        name:[
          {required:true,message:'请输入名字',trigger:'blur'},
        ],
        account:[
          {required:true,message:'请输入账号',trigger:'blur'},
          {min:3,max:8,message: '长度在 3 到 8 个字符',trigger: "blur"},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        password:[
          {required:true,message:'请输入密码',trigger:'blur'},
          {min:3,max:8,message: '长度在 3 到 15 个字符',trigger: "blur"}
        ],
        phone: [
          {required: true,message: "⼿机号不能为空",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger: "blur"}
        ]
      }
    }
  },
  methods:{
    update(row){
      console.log(row)


      this.centerDialogVisible = true
      this.$nextTick(()=>{
        this.form.id=row.id
        this.form.account=row.account
        this.form.name=row.name
        this.form.password=''
        this.form.phone=row.phone
        this.form.role=row.role+''
      })
    },
    del(id){
      console.log(id)

      this.$axios.get(this.$httpUrl+'/user/del?id='+id).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message:'操作成功！',
            type:'success'
          })
          this.loadPost()
        }else{
          this.$message({
            message:'操作失败！',
            type:'error'
          });
        }
      })
    },
    resetForm(){
      this.$refs.form.resetFields();
    },
    add(){
      this.centerDialogVisible=true
      this.$nextTick(()=>{
        this.resetForm()
      })
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message:'操作成功！',
            type:'success'
          })
          this.centerDialogVisible=false
          this.loadPost()
        }else{
          this.$message({
            message:'操作失败！',
            type:'error'
          })
        }
      })
    },
    doUpdate(){
      this.$axios.post(this.$httpUrl+'/user/update1',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.$message({
            message:'操作成功！',
            type:'success'
          })
          this.centerDialogVisible=false
          this.loadPost()
        }else{
          this.$message({
            message:'操作失败！',
            type:'error'
          })
        }
      })
    },
    save(){
      this.$refs.form.validate((valid)=>{
        if (valid){
          if (this.form.id){
            this.doUpdate();
          }else{
            this.doSave();
          }
        }else {
          console.log('error submit!!');
          return false
        }
      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    loadGet(){
      this.$axios.get(this.$httpUrl+'/user/list').then(res=>res.data).then(res=>{
        console.log(res)
      })
    },
    change(){
      this.$forceUpdate()
    },
    resetParam(){
      this.name="";
      this.sex=""
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/user/pageC',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          role:'0'
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if (res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert('获取地址失败')
        }
      })
    }
  },
  beforeMount() {
    //this.loadGet();
    this.loadPost()
  }
}
</script>

<style scoped>

</style>