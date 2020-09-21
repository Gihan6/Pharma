
import com.gihan.pharma.repo.MainRepository
import com.gihan.pharma.ui.mainData.viewModel.MainViewModel


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { MainRepository() }

}
val viewModelModule = module {

    viewModel { MainViewModel() }

}