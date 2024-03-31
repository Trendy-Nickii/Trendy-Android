package kh.edu.rupp.ite.trendy.Base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding


abstract class BaseFragmentBinding<VBing : ViewBinding> : Fragment() {

    private lateinit var mContext: Context
    protected lateinit var binding: VBing
    protected val TAG = this.javaClass.simpleName
    private lateinit var manager: FragmentManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = requireFragmentManager()
        binding = getViewBinding()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRcyBase()
        onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    abstract fun onViewCreated()

    abstract fun getViewBinding(): VBing

    fun getmContext(): Context {
        return mContext
    }



    open fun setUpRcyBase() {

    }

    open fun showFragment(fragment: Fragment, viewID: Int) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        // this will be place contain profile to it home screen
        fragmentTransaction?.replace(viewID, fragment)
        fragmentTransaction?.commit()
    }


}