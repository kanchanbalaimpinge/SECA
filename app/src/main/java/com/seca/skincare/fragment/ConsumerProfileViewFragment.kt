package com.seca.skincare.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seca.skincare.R
import com.seca.skincare.activity.BaseActivity
import com.seca.skincare.activity.HomeScreens
import com.seca.skincare.adapter.*
import com.seca.skincare.model.*
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.ListResponse
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.*
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ConsumerProfileViewFragment: Fragment() ,View.OnClickListener{
    private lateinit var profileImage: AppCompatImageView
    private lateinit var body: MultipartBody.Part
    private lateinit var requestFile: RequestBody
    private lateinit var mSaveBit: File
    private var imageChosser: ImageChosser = ImageChosser()
    var permissionsToRequest: ArrayList<String> = ArrayList()
     var permissions: ArrayList<String> = ArrayList()
    var reverse :Boolean = true
    var count: Int = 30

    var phone_no : String= ""
    var is_consultant : Boolean = false;
    private lateinit var purchaseAdapter: PurchaseAdapter
    private var purchaseList : ArrayList<PurchaseOrderModel> = ArrayList()
    private lateinit var analysisItemAdapter: AnalysisItemAdapter
    private lateinit var skinConcernsAdapter: AnalysisItemAdapter
    private lateinit var agingAdapter: AnalysisItemAdapter
    private lateinit var recommentProductAdapter: RecomentProductItemAdapter
    private var skinResult : java.util.ArrayList<SkinResultsModel> = java.util.ArrayList()
    private var skinScoreResult : java.util.ArrayList<SkinResultsModel> = java.util.ArrayList()
    private var skinCOncernResult : java.util.ArrayList<SkinResultsModel> = java.util.ArrayList()
    private var skinAgingResult : java.util.ArrayList<SkinResultsModel> = java.util.ArrayList()
    private var recommendedProductItemModel : java.util.ArrayList<RecommendedProductItemModel> =
        java.util.ArrayList()

    private lateinit var resultsView: RelativeLayout
    lateinit var scoreDate: AppCompatTextView
    lateinit var scoreVAlue: AppCompatTextView
    lateinit var yourSkinType: AppCompatTextView
    lateinit var scoreKey: AppCompatTextView
    lateinit var scoreREsultView: LinearLayout
    lateinit var noScoreREsultView: LinearLayout
    lateinit var noREsultView: LinearLayout

    lateinit var overallScoreView: RelativeLayout

    lateinit var scoreResultView: RecyclerView
    lateinit var skinConcernView: RecyclerView
    lateinit var agingView: RecyclerView
    lateinit var product_list_rv: RecyclerView
    lateinit var purchaseView: RecyclerView
    lateinit var profileView: CircleImageView
    lateinit var tvName: AppCompatTextView
    lateinit var tvPhone: AppCompatTextView
    lateinit var tvPurchase : AppCompatTextView
    lateinit var tvMoneySpent : AppCompatTextView
    lateinit var ivWhtsapp : AppCompatImageView
    lateinit var ivBack : AppCompatImageView
    lateinit var skinImageView: AppCompatImageView
    lateinit var analysisDate: AppCompatTextView
    lateinit var view1: View

    companion object {
        fun newInstance() = ConsumerProfileViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view1 =  inflater.inflate(R.layout.fragment_consultant_view, container, false)

        purchaseView = view1.findViewById(R.id.rv)

        var purchaseView = view1.findViewById(R.id.purchase_history) as RelativeLayout
        var sortView = view1.findViewById(R.id.sort) as AppCompatImageView

        profileImage = view1.findViewById(R.id.profileImage) as AppCompatImageView
        tvName = view1.findViewById(R.id.name) as AppCompatTextView
        tvPhone = view1.findViewById(R.id.phone) as AppCompatTextView
        tvMoneySpent = view1.findViewById(R.id.money) as AppCompatTextView
        tvPurchase = view1.findViewById(R.id.purchase) as AppCompatTextView
        ivWhtsapp = view1.findViewById(R.id.whtsapp) as AppCompatImageView
        ivBack = view1.findViewById(R.id.back) as AppCompatImageView

        resultsView = view1.findViewById(R.id.result) as RelativeLayout

        scoreResultView = view1.findViewById(R.id.result_rv) as RecyclerView
        skinConcernView = view1.findViewById(R.id.skin_concern_rv) as RecyclerView
        product_list_rv = view1.findViewById(R.id.product_list_rv) as RecyclerView
        agingView = view1.findViewById(R.id.aging_rv) as RecyclerView
        skinImageView = view1.findViewById(R.id.skin_image) as AppCompatImageView
        analysisDate = view1.findViewById(R.id.analysis_date) as AppCompatTextView

        overallScoreView = view1.findViewById(R.id.skinscores) as RelativeLayout
        noScoreREsultView = view1.findViewById(R.id.no_score_result_View) as LinearLayout
        noREsultView = view1.findViewById(R.id.no_result_View) as LinearLayout

        scoreDate = view1.findViewById(R.id.score_date) as AppCompatTextView
        scoreVAlue = view1.findViewById(R.id.score_value) as AppCompatTextView
        yourSkinType = view1.findViewById(R.id.your_skin_type) as AppCompatTextView
        scoreKey = view1.findViewById(R.id.score_key) as AppCompatTextView

        overallScoreView.setOnClickListener(this)

        resultsView.setOnClickListener(this)


        ivBack.setOnClickListener {
            var consumerProfileViewFragment  = ConsultantFragment()
            (requireActivity() as HomeScreens).replaceFragment(consumerProfileViewFragment,"CONSUMER_ID",Bundle())

        }

        ivWhtsapp.setOnClickListener {
            val message = "Hallo"

           startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            tvPhone.text.toString(),
                            message
                        )
                    )
                )
            )
        }

        purchaseView.setOnClickListener(this)
        sortView.setOnClickListener(this)


        showPurchaseList()

        getProfileData(requireArguments().getInt("CONSUMER_ID",0))
        getPurchaseList(requireArguments().getInt("CONSUMER_ID",0),10)
        setSkinData()

       // getSkinResultAnalysisList(SharedPreference.fetchPrefenceIntData(activity!!,"USER_ID"));
        getSkinResultAnalysisList(requireArguments().getInt("CONSUMER_ID",0));
        return view1

    }
    private fun setSkinData() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL

        val llm1 = LinearLayoutManager(context)
        llm1.orientation = LinearLayoutManager.VERTICAL

        val llm2 = LinearLayoutManager(context)
        llm2.orientation = LinearLayoutManager.VERTICAL

        val llm3 = LinearLayoutManager(context)
        llm3.orientation = LinearLayoutManager.VERTICAL
        purchaseView.layoutManager = llm


        val llm4 = LinearLayoutManager(context)
        llm3.orientation = LinearLayoutManager.VERTICAL
        purchaseView.layoutManager = llm

        analysisItemAdapter = AnalysisItemAdapter( requireContext(),skinScoreResult,resources.getString(R.string.overall_result_key))
        skinConcernsAdapter = AnalysisItemAdapter( requireContext(),skinCOncernResult,resources.getString(R.string.skin_concerns_key))
        agingAdapter = AnalysisItemAdapter( requireContext(),skinAgingResult,resources.getString(R.string.aging_key))
        recommentProductAdapter = RecomentProductItemAdapter( requireContext(),recommendedProductItemModel)
        scoreResultView.layoutManager = llm1

        skinConcernView.layoutManager = llm2
        agingView.layoutManager = llm3
        product_list_rv.layoutManager = llm4
        skinConcernView.setAdapter(skinConcernsAdapter)
        agingView.setAdapter(agingAdapter)
        scoreResultView.setAdapter(analysisItemAdapter)
        product_list_rv.setAdapter(recommentProductAdapter)

    }

    private fun askPermission() {
        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        (activity as BaseActivity?)!!.setPermissionsToRequest(permissions)
        (activity as BaseActivity?)!!.askPermission(object : BaseActivity.onPermissionResult{
            override fun onPermissionGranted() {
               // startActivityForResult(ImageChosser().getPickImageChooserIntent(activity), 200)
            }

            override fun onPermissiondenied() {}
        })
//        permissionsToRequest = findUnAskedPermissions(permissions)
//        //get the permissions we have asked for before but are not granted..
//        //we will store this in a global list to access later.
//
//
//        //get the permissions we have asked for before but are not granted..
//        //we will store this in a global list to access later.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (permissionsToRequest.size > 0) requestPermissions(
//                permissionsToRequest.toTypedArray(),
//                122
//            )
//        }

        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun showPurchaseList() {

        val spinner = view1!!.findViewById<Spinner>(R.id.main_spinner)

        val subTreatmentPojo =  PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")


        val subTreatmentPojoArrayList: java.util.ArrayList<ConsultantFilterModel> =
            java.util.ArrayList()

        for (i in 0..count step 10) {

            val subTreatmentPojo =
                ConsultantFilterModel("Show "+(i+10)+" items", (i+10), "22-03-2020")
            subTreatmentPojoArrayList.add(subTreatmentPojo)

        }

        val adapter =
            ConsultantFiterAdapter(requireContext(), R.layout.spinner_item_selected, subTreatmentPojoArrayList)
        //adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                //  getPurchaseList(subTreatmentPojoArrayList.get(position).description)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
//        val  data= PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")
//        val  data1= PurchaseModel( "Leny Wilkens","Full Body Specialist","24-03-2020")
//        val  data2= PurchaseModel( "Leny Wilkens","Full Body Specialist","27-03-2020")
//        val  data3= PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")
//        val  data4= PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")
//        val  data5= PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")
//        val  data6= PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")
//        val  data7= PurchaseModel( "Leny Wilkens","Full Body Specialist","22-03-2020")
//
//        purchaseList.add(data)
//        purchaseList.add(data1)
//        purchaseList.add(data2)
//        purchaseList.add(data3)
//        purchaseList.add(data4)
//        purchaseList.add(data5)
//        purchaseList.add(data6)
//        purchaseList.add(data7)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        purchaseView.layoutManager = llm
        purchaseAdapter = PurchaseAdapter( requireContext(),purchaseList)
        purchaseView.setAdapter(purchaseAdapter)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onClick(p0: View?) {


        when(p0!!.id){

            R.id.whtspp->{

                val phoneNumberWithCountryCode = "+62820000000"
                val message = "Hallo"

                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            String.format(
                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                phone_no,
                                message
                            )
                        )
                    )
                )
            }

            R.id.profileImage ->{

                askPermission()

            }

            R.id.sort ->{

                sortList(!reverse)
                reverse= !reverse

            }

            R.id.exit ->{

               SharedPreference.removeAll(activity)

            }

            R.id.advises -> {

                var advisesView = view1.findViewById(R.id.advises_view) as TextView
                var advisesViewLine = view1.findViewById(R.id.analysis_line) as View

                if(advisesView.isVisible) {
                    advisesView.visibility = View.GONE
                    advisesViewLine.visibility = View.GONE
                }
                else {
                    advisesView.visibility = View.VISIBLE
                    advisesViewLine.visibility = View.VISIBLE
                }

            }

            R.id.consultant -> {

                var consultantView = view1.findViewById(R.id.consultant_view) as LinearLayout
                var consultantLine = view1.findViewById(R.id.consultant_line) as View

                if(consultantView.isVisible) {
                    consultantView.visibility = View.GONE
                    consultantLine.visibility = View.GONE
                }
                else {
                    consultantView.visibility = View.VISIBLE
                    consultantLine.visibility = View.VISIBLE
                }

            }

            R.id.purchase_history -> {
                var purchaseArrow=  view1.findViewById(R.id.purchase_arrow) as ImageView

                if(purchaseList.size >0) {

                    var purchaseView =
                        view1.findViewById(R.id.purchase_history_view) as LinearLayoutCompat

                    if (purchaseView.isVisible){
                        rotateView(purchaseArrow,false)
                        purchaseView.visibility = View.GONE}
                    else {
                        rotateView(purchaseArrow,true)
                        purchaseView.visibility = View.VISIBLE
                    }
                }
//                showPurchaseList()
            }

            R.id.result -> {
                var resultArrow=  view1.findViewById(R.id.analysis_arrow) as ImageView

                if(!skinResult.isEmpty()) {
                    var resultView = view1.findViewById(R.id.analysisView) as LinearLayout

                    var analysis_line = view1.findViewById(R.id.analysis_line) as View

                    if (resultView.isVisible) {
                        rotateView(resultArrow,false)
                        resultView.visibility = View.GONE
                        analysis_line.visibility = View.GONE
                    } else {
                        rotateView(resultArrow,true)

                        resultView.visibility = View.VISIBLE
                        analysis_line.visibility = View.VISIBLE
                    }

                }else{

                    var analysis_line = view1.findViewById(R.id.analysis_line) as View

                    if (noREsultView.isVisible) {
                        rotateView(resultArrow,false)

                        noREsultView.visibility = View.GONE
                        analysis_line.visibility = View.GONE
                    } else {
                        rotateView(resultArrow,true)

                        noREsultView.visibility = View.VISIBLE
                        analysis_line.visibility = View.VISIBLE
                    }

                }
            }

            R.id.skinscores -> {
                var skinArrow=  view1.findViewById(R.id.skin_arrow) as ImageView

                if (!skinResult.isEmpty()) {
                    var resultView = view1.findViewById(R.id.score_result_View) as LinearLayout

                    var analysis_line = view1.findViewById(R.id.skin_lineView) as View

                    if (resultView.isVisible) {
                        rotateView(skinArrow,false)
                        resultView.visibility = View.GONE
                        analysis_line.visibility = View.GONE
                    } else {
                        rotateView(skinArrow,true)

                        resultView.visibility = View.VISIBLE
                        analysis_line.visibility = View.VISIBLE
                    }

                } else{

                    var analysis_line = view1.findViewById(R.id.analysis_line) as View

                    if (noScoreREsultView.isVisible) {
                        rotateView(skinArrow,false)

                        noScoreREsultView.visibility = View.GONE
                        analysis_line.visibility = View.GONE
                    } else {
                        rotateView(skinArrow,true)

                        noScoreREsultView.visibility = View.VISIBLE
                        analysis_line.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    private fun getProfileData(id: Int) {
        try {
            val utility: Utility = Utility.getInstance(activity)

            utility.showLoading(getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(activity).create(APIInterface::class.java)
            val callApi: Call<RestResponse<ConsultantProfileModel>> = apiInterface.getConsumerUserProfile(id)
            callApi.enqueue(object : Callback<RestResponse<ConsultantProfileModel>> {
                override fun onResponse(
                    call: Call<RestResponse<ConsultantProfileModel>>,
                    response: Response<RestResponse<ConsultantProfileModel>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {


                        try{

                            tvName.setText(response.body()!!.data.username)
                            tvMoneySpent.setText("IDR "+response.body()!!.data.money_spent.toString())
                            tvPurchase.setText(response.body()!!.data.purchases.toString())
                            tvPhone.setText(response.body()!!.data.phone_code+response.body()!!.data.phone_no)

                            if(!TextUtils.isEmpty(response.body()!!.data.image_url)) {
                                Glide.with(requireActivity())
                                    .load(response.body()!!.data.image_url).into(profileImage)
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }





                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(activity,response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RestResponse<ConsultantProfileModel>>, t: Throwable) {
                    Log.e("exception_msg", t.message!!)
                    utility.hideLoading()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getWebinars() {
        try {
            val utility: Utility = Utility.getInstance(activity)

            utility.showLoading(getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(activity).create(APIInterface::class.java)
            val callApi: Call<RestResponse<WebinarModel>> = apiInterface.getWebinars()
            callApi.enqueue(object : Callback<RestResponse<WebinarModel>> {
                override fun onResponse(
                    call: Call<RestResponse<WebinarModel>>,
                    response: Response<RestResponse<WebinarModel>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {



                        if(response.body()?.data!= null ){
                           var advisesView=  view1.findViewById(R.id.advises_view) as TextView
                            advisesView.setText(response.body()!!.data.title)
                        }

                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(activity,response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RestResponse<WebinarModel>>, t: Throwable) {
                    Log.e("exception_msg", t.message!!)
                    utility.hideLoading()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String> {
        val result: ArrayList<String> = ArrayList()
        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }
        return result
    }

    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(requireContext(),permission) === PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }
    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    //  picUri = getRealPathFromURI(data.getData());
                    val path1: String = imageChosser.getRealPathFromURI(data.data, activity)
                    setImageafterCrop(path1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                val path: String = imageChosser.camProfilePic.getPath()
                Log.e("CAMERA_PATH", path)
                setImageafterCrop(path)
            }
        } else {
            val path: String = imageChosser.camProfilePic.getPath()
            Log.e("CAMERA_PATH", path)
            setImageafterCrop(path)
        }
    }

    private fun setImageafterCrop(s: String) {
        try {
            mSaveBit = File(s)
            val filePath: String = mSaveBit.getPath()
            var bitmap = BitmapFactory.decodeFile(filePath)
            val bitmap1: Bitmap = imageChosser.rotaterequiedImage(filePath, bitmap)
            if (bitmap1 != null) {
                bitmap = bitmap1
            }
            if (bitmap != null) {
                requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(),mSaveBit)
                body =
                    createFormData("profile_image", mSaveBit.getName(), requestFile)
                Log.e("bodyyyyyyyyyyyyyyy", body.toString())
                profileImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 160, 160, false))
                uploadDocument()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun uploadDocument() {
        try {
            val utility = Utility.getInstance(context)

            val folder_name: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), "khk")
            utility.showLoading(getString(R.string.please_wait))
            val apiInterface = APIClient.getClient(context).create(APIInterface::class.java)
            var callApi: Call<RestResponse<AuthResponse>>? = null
            callApi = apiInterface.updateUserProfile(
                 folder_name, body
            )
            callApi!!.enqueue(object : Callback<RestResponse<AuthResponse>?> {
                override fun onResponse(
                    call: Call<RestResponse<AuthResponse>?>,
                    response: Response<RestResponse<AuthResponse>?>
                ) {
                    utility.hideLoading()
                    Log.e("response_msg", response.code().toString())
                    if (response.code() == 200) {
                        Toast.makeText(
                            context,
                            resources.getString(R.string.file_uploaded_successfully),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (response.code() == 401) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else if (response.code() == 422) {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RestResponse<AuthResponse>?>, t: Throwable) {
                    Log.e("exception_msg", t.message!!)
                    utility.hideLoading()
                }
            })
        } catch (e: Exception) {
            e.message
        }
    }

    public fun getPurchaseList(id: Int,pageSize:Int) = try {
        val utility: Utility = Utility.getInstance(activity)
        purchaseList.clear()

        val apiInterface: APIInterface =
            APIClient.getClient1(activity).create(APIInterface::class.java)
        val callApi: Call<ListResponse<PurchaseOrderModel>> = apiInterface.getUserPurchaseHistoryList(id,pageSize)
        callApi.enqueue(object : Callback<ListResponse<PurchaseOrderModel>> {
            override fun onResponse(call: Call<ListResponse<PurchaseOrderModel>>,
                response: Response<ListResponse<PurchaseOrderModel>>) {
                utility.hideLoading()
                if (response.code() == 200) {

                    purchaseList.addAll(response.body()!!.result)

                    setAdapter()

                } else if (response.code() == 400) {
                    val message: String = ErrorResponseHandler.parseError(response)
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    utility.hideLoading()
                } else {
                    Toast.makeText(activity,response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ListResponse<PurchaseOrderModel>>, t: Throwable) {
                Log.e("exception_msg", t.message!!)
                utility.hideLoading()
            }
        })
    } catch (e: Exception) {
        e.printStackTrace()
    }

    private fun setAdapter() {

        if(purchaseAdapter == null) {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            purchaseView.layoutManager = llm
            purchaseAdapter = PurchaseAdapter( requireContext(),purchaseList)
            purchaseView.setAdapter(purchaseAdapter)
        }else
            purchaseAdapter.notifyDataSetChanged()



    }

    fun sortList(reverse:Boolean){


        Collections.sort(purchaseList,  object: Comparator<PurchaseOrderModel> {


            var f =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

            override fun compare(p0: PurchaseOrderModel?, p1: PurchaseOrderModel?): Int {
                try {
                    return f.parse(p0!!.updated_at).compareTo(f.parse(p1!!.updated_at));
                } catch (e: ParseException) {
                    throw  IllegalArgumentException(e);
                }
            }
        })

        if(reverse) {
            Collections.reverse(purchaseList)
        }

        setAdapter()
    }

    public fun getSkinResultAnalysisList(id:Int) = try {
        val utility: Utility = Utility.getInstance(activity)
        purchaseList.clear()

        val apiInterface: APIInterface =
            APIClient.getClient1(activity).create(APIInterface::class.java)
        val callApi: Call<RestResponse<AIDataModel>> = apiInterface.getUserLatestSkinScoreCard(id)
        callApi.enqueue(object : Callback<RestResponse<AIDataModel>> {
            override fun onResponse(
                call: Call<RestResponse<AIDataModel>>,
                response: Response<RestResponse<AIDataModel>>
            ) {
                utility.hideLoading()
                if (response.code() == 200 && context != null) {
                    if(response.body()!!.data.AI_result[0].scores.size>0) {

                        skinResult.addAll(response.body()!!.data.AI_result[0].scores)
                        recommendedProductItemModel.addAll(response.body()!!.data.AI_result[0].recommended_products)

                        val iterator = skinResult.listIterator()
                        while (iterator.hasNext()) {
                            val skinResultsModel = iterator.next()

                            when (skinResultsModel.category) {

                                resources.getString(R.string.overall_result_key) -> skinScoreResult.add(
                                    skinResultsModel
                                )

                                resources.getString(R.string.skin_concerns_key) -> skinCOncernResult.add(
                                    skinResultsModel
                                )

                                resources.getString(R.string.aging_key) -> skinAgingResult.add(
                                    skinResultsModel
                                )


                            }
                            // ...
                        }

                        analysisItemAdapter.notifyDataSetChanged()
                        skinConcernsAdapter.notifyDataSetChanged()
                        agingAdapter.notifyDataSetChanged()
                        recommentProductAdapter.notifyDataSetChanged()

                        Glide.with(activity!!)
                            .load(response.body()!!.data.AI_result[0].result_image_url)
                            .into(skinImageView)

                        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                        val output = SimpleDateFormat("dd MMMM")
                        val output1 = SimpleDateFormat("yyyy")

                        var d: Date? = null
                        try {
                            d = input.parse(response.body()!!.data.AI_result[0].updated_at)
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                        val formatted: String = output.format(d)
                        val formatted1: String = output1.format(d)
                        Log.i("DATE", "" + formatted+" "+formatted1)
                        analysisDate.setText(formatted+" of "+formatted1)
                        scoreDate.setText(formatted+" of "+formatted1)

                        yourSkinType.text = getString(R.string.your_skin_type) +" "+ response.body()!!.data.AI_result[0].skin_type
                        scoreVAlue.setText(response.body()!!.data.AI_result[0].skin_health_score.toString())
//                    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
//                    val output = SimpleDateFormat("dd MMMM yyyy")
//
//                    var d: Date? = null
//                    try {
//                        d = input.parse(response.body()!!.data.AI_result[0].result_image)
//                    } catch (e: ParseException) {
//                        e.printStackTrace()
//                    }
//                    val formatted: String = output.format(d)
//
//                    analysisDate.setText(formatted)

                        resultsView.callOnClick()
                        overallScoreView.callOnClick()
                    }
                } else if (response.code() == 400 && context != null) {
                    val message: String = ErrorResponseHandler.parseError(response)
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    utility.hideLoading()
                } else {
                    if (context != null){
                        Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<RestResponse<AIDataModel>>, t: Throwable) {
                Log.e("exception_msg", t.message!!)
                utility.hideLoading()
            }
        })
    } catch (e: Exception) {
        e.printStackTrace()
    }

    private fun rotateView(advisesArrow: ImageView,upSide :Boolean) {

        val animSet = AnimationSet(true)
        animSet.interpolator = DecelerateInterpolator()
        animSet.fillAfter = true
        animSet.isFillEnabled = true
        val animRotate : RotateAnimation
        if(upSide) {
            animRotate  = RotateAnimation(
                0.0f, -180.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )

        }else{
            animRotate  = RotateAnimation(
                -180.0f, 0.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
        }

        animRotate.duration = 100
        animRotate.fillAfter = true
        animSet.addAnimation(animRotate)

        advisesArrow.startAnimation(animSet)

    }


}