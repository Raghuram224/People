package com.example.everybody.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load

import com.example.everybody.R
import com.example.everybody.databinding.FragmentPersonDetailsBinding
import com.example.everybody.factoryMethods.ShowPersonsFactory

import com.example.everybody.viewmodels.ShowPersonsViewModel
import kotlinx.coroutines.launch



class PersonDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPersonDetailsBinding
    private lateinit var viewModel: ShowPersonsViewModel
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_person_details,container,false)
        viewModel = ViewModelProvider(requireActivity(), ShowPersonsFactory(requireActivity().application))[ShowPersonsViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            firstName.text = viewModel.passingData.name.first
            lastName.text = viewModel.passingData.name.last
            cellNo.text= viewModel.passingData.cell
            city.text = viewModel.passingData.location.city
            dob.text = viewModel.passingData.dob.date.substring(0,10)
            gender.text= viewModel.passingData.gender.replaceFirstChar { it.uppercase() }
            email.text = viewModel.passingData.email
            nationality.text = viewModel.passingData.location.country

            circleImageView.load(viewModel.passingData.picture.large)

            lifecycleScope.launch {
                loadWeatherDeatails()
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_to_show_persons_fragment)
        }
        binding.weatherBtn.setOnClickListener {
            if (viewModel.weatherDetails.body()!=null){
                val weatherDeatils = viewModel.weatherDetails.body()?.current

                bundle = Bundle()
                val temp_c = weatherDeatils?.tempC.toString()+"° c"
                val image = "http:"+weatherDeatils?.condition?.icon
                val airQuality= viewModel.getAirQuality(weatherDeatils?.airQuality?.usEpaIndex.toString().toInt())
                val humidity = weatherDeatils?.humidity.toString()+" %"
                val  windSpeed = weatherDeatils?.windSpeed.toString()
                val climate = weatherDeatils?.condition?.text.toString()

                bundle.putString("temp_c",temp_c)
                bundle.putString("image",image)
                bundle.putString("airQuality",airQuality)
                bundle.putString("humidity",humidity)
                bundle.putString("windSpeed",windSpeed)
                bundle.putString("climate",climate)

                if (viewModel.weatherDetails.body()!=null){
                    findNavController().navigate(R.id.action_personDetailsFragment_to_weatherReportFragment,bundle)
                } else{
                    Toast.makeText(requireContext(),"Please wait until we process",Toast.LENGTH_SHORT).show()
                }


            }else{
                Toast.makeText(requireContext(),"Please wait until we process",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private suspend fun loadWeatherDeatails(){
        val latitude =  viewModel.passingData.location.coordinates.latitude
        val longitude =  viewModel.passingData.location.coordinates.longitude
//        Log.i("Weather",latitude+"/"+longitude)
        viewModel.userCurrentWeather("$latitude,$longitude" )
    }


}