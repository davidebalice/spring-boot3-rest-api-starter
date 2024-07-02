package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.model.Gallery;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
}
