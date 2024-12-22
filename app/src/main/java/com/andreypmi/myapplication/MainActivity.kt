package com.andreypmi.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.andreypmi.myapplication.databinding.ActivityMainBinding
import com.andreypmi.myapplication.databinding.ItemContactBinding
import com.andreypmi.myapplication.domain.ContactAdapter
import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.presentation.ItemMoveCallback
import com.andreypmi.myapplication.presentation.viewmodel.ContactUiState
import com.andreypmi.myapplication.presentation.viewmodel.ContactViewModel
import com.andreypmi.myapplication.presentation.viewmodel.ContactViewModelFactory
import kotlinx.coroutines.launch

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        bindingMain = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(bindingMain.root)
//        setSupportActionBar(bindingMain.toolbar)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        viewModel = ViewModelProvider(this, ContactViewModelFactory())[ContactViewModel::class.java]
//        setupRecyclerView()
//        setupFab()
//        setupSelectionMode()
//        observeUiState()
//        bindingMain.toolbar.setNavigationIcon(R.drawable.ic_close)
//
//
//        bindingMain.toolbar.setNavigationOnClickListener {
//            viewModel.toggleSelectionMode()
//        }
//    }

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var itemTouchHelper: androidx.recyclerview.widget.ItemTouchHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationIcon(R.drawable.ic_close)


        viewModel = ViewModelProvider(this, ContactViewModelFactory())[ContactViewModel::class.java]
        setupRecyclerView()
        setupFab()
        setupSelectionMode()
        observeUiState()
        binding.toolbar.setNavigationOnClickListener {
            viewModel.toggleSelectionMode()
        }
    }


    private fun setupRecyclerView() {
        Log.d("AAAEW","+++")
        contactAdapter = ContactAdapter(
            onItemClick = { contact -> onContactClick(contact) },
            onItemLongClick = { contact -> onContactLongClick(contact) },
            isContactSelected = { contact -> viewModel.isContactSelected(contact) }
        )

        binding.contactsRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }

        val callback: androidx.recyclerview.widget.ItemTouchHelper.Callback =
            ItemMoveCallback(contactAdapter) { from, to -> viewModel.moveContact(from, to) }
        itemTouchHelper = androidx.recyclerview.widget.ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.contactsRecyclerView)
    }

    private fun setupFab() {
        binding.addContactFab.setOnClickListener {
            //  TODO: Open Add contact dialog/Activity
            onAddContactClick()
        }
    }

    private fun setupSelectionMode() {
        binding.cancelSelectionButton.setOnClickListener {
            viewModel.exitSelectionMode()
        }
        binding.deleteSelectionButton.setOnClickListener {
            viewModel.deleteSelectedContacts()
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                updateUi(uiState)
            }
        }
    }

    private fun updateUi(uiState: ContactUiState) {
        contactAdapter.submitList(uiState.contacts)
        contactAdapter.isSelectionMode = uiState.isSelectionMode
        binding.selectionButtonsLayout.isVisible = uiState.isSelectionMode
        binding.addContactFab.isVisible = !uiState.isSelectionMode
        binding.toolbar.setNavigationIcon(if (uiState.isSelectionMode) R.drawable.ic_add else R.drawable.ic_close)
        binding.contactsRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun onContactClick(contact: ContactEntity) {
        if(viewModel.uiState.value.isSelectionMode){
            viewModel.selectContact(contact)
        }else {
            onEditContactClick(contact)
        }
    }

    private fun onContactLongClick(contact: ContactEntity) {
        if(!viewModel.uiState.value.isSelectionMode){
          //  viewModel.toggleSelectionMode()
                //viewModel.selectContact(contact)
        }
    }
    private fun onAddContactClick() {
        // TODO: Implement navigation to add contact activity/fragment
    }

    private fun onEditContactClick(contact: ContactEntity) {
        // TODO: Implement navigation to edit contact activity/fragment
    }
    }
