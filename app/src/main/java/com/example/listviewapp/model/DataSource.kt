package com.example.listviewapp.model

class DataSource {
 companion object
 {
     fun CreateDataSource():ArrayList<Company>
     {
        val list=ArrayList<Company>()
         list.add(Company("DoodleBlue","Silver Coin","D"))
         list.add(Company("TSC","Blue Coin","T"))
         list.add(Company("Cognizant","Red Coin","C"))
         list.add(Company("SpurTree","Pink Coin","S"))
         list.add(Company("GreyTip","Purple Coin","G"))
         list.add(Company("Google","Golden Coin","G"))
         list.add(Company("Microsoft","Yellow Coin","M"))
         return  list
     }
 }
}