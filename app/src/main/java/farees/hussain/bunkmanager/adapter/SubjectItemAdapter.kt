package farees.hussain.bunkmanager.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import farees.hussain.bunkmanager.R
import farees.hussain.bunkmanager.databinding.ItemSubjectsBinding
import farees.hussain.bunkmanager.db.model.Subject
import kotlinx.android.synthetic.main.subjects_header.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

// item view types for header and others
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class SubjectItemAdapter(
    val clickListner: SubjectItemClickListner
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(SubjectItemDiffCallBack()) {


    /*
        to add Header dataitem at the top of the list
        submit a list of DataItem to the diffutil
     */
    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addHeaderAndSubmitList(list: List<Subject>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SubjectItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
    /*
        sets Item ViewType according to the Header or Subject Item of the DataItem
     */
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SubjectItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
    /*
        returns a Recycler ViewHolder according to the viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val subjectItem = getItem(position) as DataItem.SubjectItem
                holder.bind(subjectItem.subject, clickListner)
            }
        }
    }

    /*
        Custom ViewHolder for Header
        // todo change the layout for header
     */
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            // from is use to inflate the layout int the onCreateViewHolder
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.subjects_header, parent, false)
                view.text.text = "Subjects Attendance Here"
                return TextViewHolder(view)
            }
        }
    }


    class ViewHolder private constructor(val binding: ItemSubjectsBinding):RecyclerView.ViewHolder(binding.root){
        // todo use databinding to the fullest
        fun bind(item: Subject, clickListner: SubjectItemClickListner){
            item.percentageAttendance = if(item.totalClasses == 0) 0.0 else Math.round((item.classesAttended.toDouble()*100/item.totalClasses).toDouble() * 10.0)/10.0
            binding.subject = item
            binding.itemclickListner = clickListner
            binding.executePendingBindings()

            /*attendance status if less than 75 and if greater than 75%*/
            fun changeStatus() {
                if(item.totalClasses == 0){
                    binding.tvStatus.text = "Attendance Not Yet Started"
                    return
                }
                binding.tvCurrentAttendance.text = "Current Attendance : ${item.classesAttended}/${item.totalClasses}"
                if (item.percentageAttendance!! < item.requiredPercentageAttendance) {
                    var noOfClassesToAttend = 3 * item.totalClasses - 4 * item.classesAttended
                    if (noOfClassesToAttend < 0) noOfClassesToAttend++
                    item.classesMustAttend = noOfClassesToAttend
                    binding.tvStatus.text =
                        "To get More Than ${item.requiredPercentageAttendance}% Attend $noOfClassesToAttend classes"
                    binding.tvStatus.setTextColor(Color.RED)
                } else {
                    var noOfClassesCanBeBunked = 0
                    var a = item.classesAttended
                    var t = item.totalClasses
                    while(a*100/t >= item.requiredPercentageAttendance.toDouble()){
                        noOfClassesCanBeBunked++
                        t++
                    }
                    if(a*100/t<item.requiredPercentageAttendance)noOfClassesCanBeBunked--
                    item.classesCanBeBunked = noOfClassesCanBeBunked
                    binding.tvStatus.text = if(noOfClassesCanBeBunked>0) "You Bunk $noOfClassesCanBeBunked classes" else "You Can't Bunk any Class Now"
                    binding.tvStatus.setTextColor(Color.MAGENTA)
                }
            }
            changeStatus()
            binding.buBunk.setOnClickListener {
                item.totalClasses++
                item.currentAttendance = "Current Attendance ${item.classesAttended}/${item.totalClasses}"
                var percentage = item.classesAttended.toDouble() * 100.0/item.totalClasses
                item.percentageAttendance = Math.round(percentage * 10.0)/10.0

                binding.tvCurrentAttendance.text = "Current Attendance : ${item.classesAttended}/${item.totalClasses}"
//                binding.tvPercent.text = "${item.percentageAttendance}%"
                changeStatus()
            }
            binding.buAttend.setOnClickListener {
                item.classesAttended++
                item.totalClasses++
                item.currentAttendance = "Current Attendance : ${item.classesAttended}/${item.totalClasses}"
                var percentage = item.classesAttended.toDouble() * 100.0/item.totalClasses
                item.percentageAttendance = Math.round(percentage * 10.0)/10.0

                binding.tvCurrentAttendance.text = item.currentAttendance
//                binding.tvPercent.text = "${item.percentageAttendance}%"
                changeStatus()
                binding.subject = item
            }
        }

        // from is used to set inflate the layout in the onCreateViewHolder
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSubjectsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

// diff util is changed it accepts a list of DataItem
class SubjectItemDiffCallBack : DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return  oldItem == newItem
    }
}

class SubjectItemClickListner(val clickListener: (subject: Subject)-> Unit){

    //todo add click listeners for bunk, attend and no class today buttons
    fun itemClick(subject: Subject) {
        subject.subjectName = "changed"
        clickListener(subject)
    }
}

/*  Dataitem is used as a list to use in the recycler view
    it has SubjectItem used in items
    and Header used as a Header
 */
sealed class DataItem{
    data class SubjectItem(val subject: Subject) : DataItem() {
        override val id = subject.id!!
    }

    object Header:DataItem(){
        override val id = Long.MIN_VALUE
    }
    abstract val id:Long
}