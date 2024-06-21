package viewmodel

enum class Status {
   IDLE, LOADING,SUCCESS,ERROR;
    
    fun isSucess():Boolean =this==SUCCESS;
    
  
}