package com.namolog.message.service.impl;

import com.namolog.message.domain.NmResource;
import com.namolog.message.repository.ResourceRepository;
import com.namolog.message.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    public NmResource getResource(int id) {
        return resourceRepository.findById(id).orElse(new NmResource());
    }

    public List<NmResource> getResourceList() {
        return resourceRepository.findAll();
    }

    public void saveResource(NmResource nmResource){
        resourceRepository.save(nmResource);
    }

    public void deleteResource(int id) {
        resourceRepository.deleteById(id);
    }
}