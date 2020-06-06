package com.namolog.message.service;



import com.namolog.message.domain.NmResource;

import java.util.List;

public interface ResourceService {
    NmResource getResource(int id);
    List<NmResource> getResourceList();
    void saveResource(NmResource NmResource);
    void deleteResource(int id);

}