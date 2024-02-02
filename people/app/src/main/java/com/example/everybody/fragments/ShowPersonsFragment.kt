package com.example.everybody.fragments



import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load

import com.example.everybody.R
import com.example.everybody.databinding.FragmentShowPersonsBinding
import com.example.everybody.factoryMethods.ShowPersonsFactory

import com.example.everybody.recyclerview.PersonItemAdapter
import com.example.everybody.retrofitAPIs.randomuserAPI.User
import com.example.everybody.viewmodels.ShowPersonsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch


class ShowPersonsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var personItemAdapter: PersonItemAdapter
    private lateinit var binding: FragmentShowPersonsBinding
    private lateinit var viewModel: ShowPersonsViewModel
    private lateinit var fusedLocationProviderClient :FusedLocationProviderClient
    private  var spanCount =2
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var searchList :MutableList<User>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_show_persons,container,false)
        viewModel = ViewModelProvider(requireActivity(),ShowPersonsFactory(requireActivity().application))[ShowPersonsViewModel::class.java]

        val orientation = resources.configuration.orientation
        spanCount = if (orientation== Configuration.ORIENTATION_LANDSCAPE)3 else 2



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerviewContainer
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        searchView =view.findViewById(R.id.search_bar)

        if (!viewModel.decideToFetchDataFrom()){
            lifecycleScope.launch{

                viewModel.randomUserApiCall()

                createRecyclerViewForApi(viewModel.userDataList)
                Toast.makeText(requireContext(),"from api",Toast.LENGTH_SHORT).show()
            }
        }else{
            viewModel.dbData = viewModel.convertEntityToData()
            createRecyclerViewForApi(viewModel.dbData)
            Toast.makeText(requireContext(),"from db",Toast.LENGTH_SHORT).show()
        }


        lifecycleScope.launch {
            getLocation()
        }

        binding.searchBar.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    filterList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }
        })

    }
    private fun filterList(query:String){ // for search view
        val filteredList= ArrayList<User>()

        if (viewModel.searchFromApi) {
            for (user in viewModel.userDataList) {
                if ((user.name.first + user.name.last).lowercase().startsWith(query, ignoreCase = false)
                    ||(user.name.first + user.name.last).lowercase().endsWith(query, ignoreCase = false)) {
                    if (!filteredList.contains(user)) filteredList.add(user)
                }
            }
        }else{
            for (user in viewModel.dbData) {
                if ((user.name.first + user.name.last).lowercase().startsWith(query, ignoreCase = false)
                    ||(user.name.first + user.name.last).lowercase().endsWith(query, ignoreCase = false)) {
                    if (!filteredList.contains(user)) filteredList.add(user)
                }
            }
        }

        if (filteredList.isEmpty()){
           println("no result found")
        }else{
                personItemAdapter.filteredItems(filteredList)

        }
    }



    private fun createRecyclerViewForApi(data:List<User>){

//        println("RE"+viewModel.userDataList)
        binding.progressBarShowPersonsFragment.isVisible=false
        personItemAdapter = PersonItemAdapter(data)
        recyclerView.adapter = personItemAdapter

        personItemAdapter.onItemClick={
            viewModel.passingData=it
            findNavController().navigate(R.id.action_to_person_details)
        }

        binding.recyclerviewContainer.layoutManager= StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL)

    }



    fun getLocation() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder(requireContext())
                .setTitle("Location Permission Needed")
                .setMessage("We need your location to show the weather in your location.")
                .setPositiveButton("OK") { _,_  ->
                    requestLocationPermission()
                }
                .setNegativeButton("Cancel") { _,_  ->
                    // Handle denial or show a message
//                    showToast("Location permission denied.")
                }
                .create()
                .show()
        } else {

            fetchLocation()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }

    private fun fetchLocation() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    // Got last known location. In some situations, this can be null.
                    location?.let {
                        val latitude = it.latitude.toString()
                        val longitude = it.longitude.toString()

                        lifecycleScope.launch{
                            println("Co ordinates $latitude,$longitude")
                            viewModel.userCurrentWeather("$latitude,$longitude")
                            updateActionBar()
                        }


                    }

                }


        }
        else{
            showToast("Location permission not granted")
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateActionBar(){
        if (viewModel.weatherStatus!=null && viewModel.weatherImg!=null){
           binding.apply {
               actionBarId.userWeather.text = viewModel.weatherStatus
               actionBarId.userWeatherImg.load(viewModel.weatherImg)
           }

        }
        else{
            binding.apply {
                actionBarId.userWeather.text = "?"
                actionBarId.userWeatherImg.setImageResource(R.drawable.cloud_img)
            }
        }
    }





}
