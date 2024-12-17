package com.epam.training.gen.ai.plugin;

import java.util.ArrayList;
import java.util.List;

import com.epam.training.gen.ai.model.UserRecord;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StorePersonalDataPlugin {

  // instead of repository
  private final List<UserRecord> userRecords = new ArrayList<>();

  public StorePersonalDataPlugin() {
    log.info("Plugin run! StorePersonalDataPlugin initialized");
  }

  @DefineKernelFunction(
    name = "get_user_data",
    description = "Gets all collected user personal data, preserved during conversation"
  )
  public List<UserRecord> getUserData() {
    log.info("Plugin run! Getting all user data");
    return userRecords;
  }

  @DefineKernelFunction(name = "add_data", description = "Adds user data gathered during conversation")
  public UserRecord addData(
    @KernelFunctionParameter(name = "recordType", description = "Type of user data") final String recordType,
    @KernelFunctionParameter(name = "content", description = "The user data by itself") final String content
  ) {

    log.info("Plugin run! Saving used data {}: {} ", recordType, content);

    final UserRecord userRecord = new UserRecord(recordType, content);
    userRecords.add(userRecord);

    return userRecord;
  }
}
