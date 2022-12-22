package com.seca.skincare.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
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
import androidx.appcompat.app.AlertDialog
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
import com.seca.skincare.activity.LoginScreens
import com.seca.skincare.adapter.AnalysisItemAdapter
import com.seca.skincare.adapter.ConsultantFiterAdapter
import com.seca.skincare.adapter.PurchaseAdapter
import com.seca.skincare.adapter.RecomentProductItemAdapter
import com.seca.skincare.model.*
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.ListResponse
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.ErrorResponseHandler
import com.seca.skincare.utils.ImageChosser
import com.seca.skincare.utils.SharedPreference
import com.seca.skincare.utils.Utility
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment: Fragment() ,View.OnClickListener{
    private lateinit var resultsView: RelativeLayout
    private lateinit var profileImage: CircleImageView
    private lateinit var body: MultipartBody.Part
    private lateinit var requestFile: RequestBody
    private lateinit var mSaveBit: File
    private var imageChosser: ImageChosser = ImageChosser()
    var permissionsToRequest: ArrayList<String> = ArrayList()
     var permissions: ArrayList<String> = ArrayList()
    var alert: AlertDialog? = null
    var count: Int = 30

    var phone_no : String= ""
    var is_consultant : Boolean = false;
    private lateinit var purchaseAdapter: PurchaseAdapter
    private lateinit var analysisItemAdapter: AnalysisItemAdapter
    private lateinit var skinConcernsAdapter: AnalysisItemAdapter
    private lateinit var agingAdapter: AnalysisItemAdapter
    private lateinit var recommentProductAdapter: RecomentProductItemAdapter
    private var purchaseList : ArrayList<PurchaseOrderModel> = ArrayList()
    private var skinResult : ArrayList<SkinResultsModel> = ArrayList()
    private var skinScoreResult : ArrayList<SkinResultsModel> = ArrayList()
    private var skinCOncernResult : ArrayList<SkinResultsModel> = ArrayList()
    private var skinAgingResult : ArrayList<SkinResultsModel> = ArrayList()
    private var recommendedProductItemModel : ArrayList<RecommendedProductItemModel> = ArrayList()


    lateinit var scoreDate: AppCompatTextView
    lateinit var scoreVAlue: AppCompatTextView
    lateinit var yourSkinType: AppCompatTextView
    lateinit var scoreKey: AppCompatTextView
    lateinit var scoreREsultView: LinearLayout
    lateinit var noScoreREsultView: LinearLayout
    lateinit var noREsultView: LinearLayout

    lateinit var overallScoreView: RelativeLayout

    lateinit var purchaseView: RecyclerView
    lateinit var scoreResultView: RecyclerView
    lateinit var skinConcernView: RecyclerView
    lateinit var agingView: RecyclerView
    lateinit var product_list_rv: RecyclerView
    lateinit var profileView: CircleImageView
    lateinit var skinImageView: AppCompatImageView
    lateinit var analysisDate: AppCompatTextView
    lateinit var purchaseView1: RelativeLayout
    lateinit var view1: View
    var reverse :Boolean = true
    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        view1 =  inflater.inflate(R.layout.fragment_home, container, false)

        purchaseView = view1.findViewById(R.id.rv)

        var advisesView1 = view1.findViewById(R.id.advises) as RelativeLayout
        purchaseView1 = view1.findViewById(R.id.purchase_history) as RelativeLayout
        scoreResultView = view1.findViewById(R.id.result_rv) as RecyclerView
        skinConcernView = view1.findViewById(R.id.skin_concern_rv) as RecyclerView
        product_list_rv = view1.findViewById(R.id.product_list_rv) as RecyclerView
        agingView = view1.findViewById(R.id.aging_rv) as RecyclerView
        skinImageView = view1.findViewById(R.id.skin_image) as AppCompatImageView
        analysisDate = view1.findViewById(R.id.analysis_date) as AppCompatTextView
        var consultantView = view1.findViewById(R.id.consultant) as RelativeLayout
         resultsView = view1.findViewById(R.id.result) as RelativeLayout
        var whtsappView = view1.findViewById(R.id.whtspp) as ImageView
        var sortView = view1.findViewById(R.id.sort) as AppCompatImageView
        var title = view1.findViewById(R.id.title) as AppCompatTextView
        var advises_title = view1.findViewById(R.id.advises_title) as AppCompatTextView
        profileImage = view1.findViewById(R.id.profileImage) as CircleImageView
        profileView = view1.findViewById(R.id.profileView) as CircleImageView
        var exitView = view1.findViewById(R.id.exit) as ImageView
        overallScoreView = view1.findViewById(R.id.skinscores) as RelativeLayout
        noScoreREsultView = view1.findViewById(R.id.no_score_result_View) as LinearLayout
        noREsultView = view1.findViewById(R.id.no_result_View) as LinearLayout

        scoreDate = view1.findViewById(R.id.score_date) as AppCompatTextView
        scoreVAlue = view1.findViewById(R.id.score_value) as AppCompatTextView
        yourSkinType = view1.findViewById(R.id.your_skin_type) as AppCompatTextView
        scoreKey = view1.findViewById(R.id.score_key) as AppCompatTextView

        overallScoreView.setOnClickListener(this)

        advisesView1.setOnClickListener(this)
        sortView.setOnClickListener(this)
        resultsView.setOnClickListener(this)
        purchaseView1.setOnClickListener(this)
        consultantView.setOnClickListener(this)
        whtsappView.setOnClickListener(this)
        exitView.setOnClickListener(this)
        profileImage.setOnClickListener(this)

        advisesView1.callOnClick()
        purchaseView.callOnClick()

      is_consultant =  SharedPreference.fetchPrefenceBoolData(context, "IS_CONSULTANT")
      //  is_consultant = false;
        title.setText(SharedPreference.fetchPrefenceData(context, "USERNAME"))
        if(is_consultant){
            consultantView.visibility = View.GONE
            var consultantLine = view1.findViewById(R.id.consultant_line) as View
            consultantLine.visibility = View.GONE
            var analysisLine = view1.findViewById(R.id.analysis_line) as View
            analysisLine.visibility = View.GONE


        }
        showPurchaseList()

        getProfileData()
        getWebinars()

        setSkinData()

      getSkinResultAnalysisList(SharedPreference.fetchPrefenceIntData(activity!!,"USER_ID"));
  //getSkinResultAnalysisList(15);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.ACCESS_MEDIA_LOCATION)

        } else {

            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        }

        (activity as BaseActivity?)!!.setPermissionsToRequest(permissions)
        (activity as BaseActivity?)!!.askPermission(object : BaseActivity.onPermissionResult{
            override fun onPermissionGranted() {
                startActivityForResult(imageChosser.getPickImageChooserIntent1(activity), 200)
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


        val subTreatmentPojoArrayList: java.util.ArrayList<ConsultantFilterModel> = ArrayList()

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

            R.id.exit -> {


                showAlertDialog(requireContext(),resources.getString(R.string.exit_app),resources.getString(R.string.are_you_want_to_logout),resources.getString(R.string.yes)
                    ,resources.getString(R.string.no),object :
                        OnButtonClickListener{
                        override fun onButtonClick(positive: Boolean) {
                            try {

                                SharedPreference.removeAll(activity)
                                startActivity(Intent(activity,LoginScreens::class.java))
                                activity!!.finishAffinity()

                            }catch (e: Exception){
                                e.printStackTrace()
                            }

                        }
                    }
                )
            }
            R.id.advises -> {

                var advisesView = view1.findViewById(R.id.advises_view) as TextView
                var advisesTitle=  view1.findViewById(R.id.advises_title) as TextView
                var advisesArrow=  view1.findViewById(R.id.advises_arrow) as ImageView



                var advisesViewLine = view1.findViewById(R.id.advised_line) as View

                if(advisesView.isVisible) {
                    rotateView(advisesArrow,false)
                    advisesView.visibility = View.GONE
                    advisesTitle.visibility = View.GONE
                    advisesViewLine.visibility = View.GONE
                }
                else {
                    rotateView(advisesArrow,true)
                    advisesView.visibility = View.VISIBLE
                    advisesTitle.visibility = View.VISIBLE
                    advisesViewLine.visibility = View.VISIBLE
                }

            }
            R.id.consultant -> {

                var consultantView = view1.findViewById(R.id.consultant_view) as LinearLayout
                var consultantLine = view1.findViewById(R.id.consultant_line) as View
                var consultantArrow=  view1.findViewById(R.id.consultant_arrow) as ImageView

                if(consultantView.isVisible) {
                    rotateView(consultantArrow,false)

                    consultantView.visibility = View.GONE
                    consultantLine.visibility = View.GONE
                }
                else {
                    rotateView(consultantArrow,true)

                    consultantView.visibility = View.VISIBLE
                    consultantLine.visibility = View.VISIBLE
                }

            }

            R.id.purchase_history -> {
                if(purchaseList.size >0) {

                    var purchaseView =
                        view1.findViewById(R.id.purchase_history_view) as LinearLayoutCompat
                    var purchaseArrow=  view1.findViewById(R.id.purchase_arrow) as ImageView

                    if (purchaseView.isVisible) {
                        rotateView(purchaseArrow, false)

                        purchaseView.visibility = View.GONE
                    }
                    else {
                        rotateView(purchaseArrow, true)

                        purchaseView.visibility = View.VISIBLE
                    }
                }

            }


            R.id.result -> {
                var resultArrow=  view1.findViewById(R.id.analysis_arrow) as ImageView

                if(!skinResult.isEmpty()) {
                    var resultView = view1.findViewById(R.id.analysisView) as LinearLayout

                    var analysis_line = view1.findViewById(R.id.analysis_line) as View

                    if (resultView.isVisible) {
                        rotateView(resultArrow, false)

                        resultView.visibility = View.GONE
                        analysis_line.visibility = View.GONE
                    } else {
                        rotateView(resultArrow, true)

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

    private fun getProfileData() {
        try {
            val utility: Utility = Utility.getInstance(activity)

            utility.showLoading(getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(activity).create(APIInterface::class.java)
            val callApi: Call<RestResponse<ProfileModel>> = apiInterface.userProfile
            callApi.enqueue(object : Callback<RestResponse<ProfileModel>> {
                override fun onResponse(
                    call: Call<RestResponse<ProfileModel>>,
                    response: Response<RestResponse<ProfileModel>>
                ) {
                    try {
                        utility.hideLoading()

                        if (response.code() == 200) {


                            if (response.body()?.data != null && !response.body()!!.data.is_consultant && response.body()!!.data.consultant != null) {


                                var consultantName =
                                    view1.findViewById(R.id.consultant_name) as TextView
                                consultantName.setText(response.body()!!.data.consultant.username)
                                phone_no =
                                    response.body()!!.data.consultant.phone_code + response.body()!!.data.consultant.phone_no
                                if (response.body()!!.data.consultant.image_url != null && !TextUtils.isEmpty(response.body()!!.data.consultant.image_url)) {
                                   try {

                                       if(context != null ) {
                                           Glide.with(requireContext()!!)
                                               .load(response.body()!!.data.consultant.image_url)
                                               .placeholder(R.drawable.noprofile)
                                               .into(profileView)
                                       }
                                   }catch ( e: Exception){
                                       e.printStackTrace()
                                   }
                                }


                            }

                            if (response.body()?.data != null && response.body()!!.data.image_url != null) {
                                if(context != null ) {

                                    Glide.with(requireContext()!!)
                                        .load(response.body()!!.data.image_url)
                                        .into(profileImage)
                                    SharedPreference.savePreferenceData(
                                        activity,
                                        "IMAGE",
                                        response.body()?.data?.image_url
                                    )
                                }

                            }
                            if (response.body()?.data != null && response.body()!!.data.address != null) {

                                SharedPreference.savePreferenceData(
                                    activity,
                                    "ADDRESS",
                                    response.body()?.data?.address
                                )
                            }

                            if (response.body()?.data != null && response.body()!!.data.city != null) {

                                SharedPreference.savePreferenceData(
                                    activity,
                                    "CITY",
                                    response.body()?.data?.city
                                )
                            }
                            getPurchaseList(response.body()!!.data.id,10)
                        } else if (response.code() == 400) {
                            val message: String = ErrorResponseHandler.parseError(response)
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                            utility.hideLoading()
                        } else {
                            Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<RestResponse<ProfileModel>>, t: Throwable) {
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
                           var advisesTitle=  view1.findViewById(R.id.advises_title) as TextView
                            advisesView.setText(response.body()!!.data.description)
                            advisesTitle.setText(response.body()!!.data.title)
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

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String = context.getString(R.string.yes),
        negativeButtonText: String = context.getString(R.string.no),
        listener: OnButtonClickListener,
        cancelable: Boolean = false
    ) {
        if (alert != null && alert!!.isShowing) {
            alert!!.dismiss()
        }
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(message)
            .setCancelable(cancelable)
            .setPositiveButton(positiveButtonText) { dialog, id ->
                listener.onButtonClick(true)
                dialog.cancel()
            }
            .setNegativeButton(negativeButtonText) { dialog, _ ->
               // listener.onButtonClick(false)
                dialog.cancel()
            }
        alert = dialogBuilder.create()
        alert!!.show()
    }
    interface OnButtonClickListener {
        fun onButtonClick(positive: Boolean)
    }


    public fun getPurchaseList(id:Int,pageSize:Int) = try {
        val utility: Utility = Utility.getInstance(activity)
        purchaseList.clear()

        val apiInterface: APIInterface =
            APIClient.getClient1(activity).create(APIInterface::class.java)
        val callApi: Call<ListResponse<PurchaseOrderModel>> = apiInterface.getUserPurchaseHistoryList(pageSize)
        callApi.enqueue(object : Callback<ListResponse<PurchaseOrderModel>> {
            override fun onResponse(
                call: Call<ListResponse<PurchaseOrderModel>>,
                response: Response<ListResponse<PurchaseOrderModel>>
            ) {
                utility.hideLoading()
                if (response.code() == 200) {

                    purchaseList.addAll(response.body()!!.result)

                    setAdapter()
                    purchaseView1.callOnClick()
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

                        yourSkinType.text = getString(R.string.your_skin_type)+" "+ response.body()!!.data.AI_result[0].skin_type
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
//    override fun requireContext():Context
//    {
//        try {
//            if (context!! != null) {
//                return super.requireContext()
//            }
//        }catch (e: Exception ){
//            e.printStackTrace()
//        }
//        return activity!!
//    }
}

//1.Still APP CRASHED on when tap buy
//2.Ai scan scroll view is so laggy it's very hard to scroll
//3.Ai scan images loading takes much time
//4.Ai scan -Need loader in the images  instead of dummy image
//6.Images streched AI scan history
//7.Please change the remove icon in some images user not able to see cross icon
//8.Ai scan images history info icon is missing
//9.Ai scan & home screen history of results UI lags need aligned properly
//10.Decrease the size of the app
//11.Profile icon is missing for new users
//12.Check the arrow button in detail screen when Up side then no data show when downside should be show all data .
//(Right now it's still static position In down side ) check in whole app where we show large data
//13. Recent clients show data  10 clients but  in the page show 11
//But user applied filter 20
//Filteration not working properly1.Still APP CRASHED on when tap buy
