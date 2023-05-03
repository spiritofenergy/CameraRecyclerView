package com.kodex.camerarecyclerview

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kodex.camerarecyclerview.databinding.FragmentDoorBinding
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer

class DoorFragment: Fragment() {
    private var _binding: FragmentDoorBinding? = null

    private val binding get() = _binding!!
    var libVLC: LibVLC? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDoorBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libVLC = LibVLC(activity)
        val url = getString(R.string.rtspUrl84)
        val media = Media(libVLC, Uri.parse(url))
        media.addOption("--aout=opensles")
        media.addOption("--audio-time-stretch")
        //media.addOption("--no-drop-late-frames");
        media.addOption("-vvv") // verbosity
        val mediaPlayer = MediaPlayer(libVLC)
        mediaPlayer.media = media
        mediaPlayer.vlcVout.setVideoSurface(
            binding.contentMain.videoView.holder.surface,
            binding.contentMain.videoView.holder
        )
        mediaPlayer.vlcVout.setWindowSize(
            binding.contentMain.videoView.width,
            binding.contentMain.videoView.height
        )
        mediaPlayer.vlcVout.attachViews()
        mediaPlayer.play()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}