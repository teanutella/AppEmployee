package ca.ulaval.glo4003.appemployee.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.appemployee.domain.project.Project;
import ca.ulaval.glo4003.appemployee.domain.project.ProjectProcessor;
import ca.ulaval.glo4003.appemployee.domain.repository.ProjectRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.TaskRepository;
import ca.ulaval.glo4003.appemployee.domain.repository.UserRepository;
import ca.ulaval.glo4003.appemployee.domain.task.Task;
import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.persistence.RepositoryException;
import ca.ulaval.glo4003.appemployee.web.converters.ProjectConverter;
import ca.ulaval.glo4003.appemployee.web.converters.TaskConverter;
import ca.ulaval.glo4003.appemployee.web.converters.UserConverter;
import ca.ulaval.glo4003.appemployee.web.viewmodels.ProjectViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.TaskViewModel;
import ca.ulaval.glo4003.appemployee.web.viewmodels.UserViewModel;

@Service
public class ProjectService {

	private ProjectConverter projectConverter;
	private TaskConverter taskConverter;
	private UserConverter userConverter;
	private ProjectRepository projectRepository;
	private TaskRepository taskRepository;
	private UserRepository userRepository;
	private ProjectProcessor projectProcessor;

	@Autowired
	public ProjectService(ProjectConverter projectConverter,
			TaskConverter taskConverter, UserConverter userConverter,
			ProjectRepository projectRepository, TaskRepository taskRepository,
			UserRepository userRepository, ProjectProcessor projectProcessor) {
		this.userConverter = userConverter;
		this.projectConverter = projectConverter;
		this.taskConverter = taskConverter;
		this.projectRepository = projectRepository;
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
		this.projectProcessor = projectProcessor;
	}

	public Collection<ProjectViewModel> retrieveAllProjects() {
		return projectConverter.convert(projectRepository.findAll());
	}

	public void addProject(Project project) {
		try {
			projectRepository.store(project);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void updateProject(String projectId, ProjectViewModel viewModel) {

		Project project = projectRepository.findById(projectId);
		project.setName(viewModel.getName());

		if (!viewModel.getUserEmail().isEmpty()
				&& userRepository.findByEmail(viewModel.getUserEmail()) != null) {
			project.addEmployeeToProject(viewModel.getUserEmail());
			addEmployeToTasksOfProject(projectId, viewModel.getUserEmail());
		}

		try {
			projectRepository.store(project);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	private void addEmployeToTasksOfProject(String projectId, String userEmail) {
		for (Task task : getAllTasksByProjectId(projectId)) {
			if (!task.userIsAssignedToTask(userEmail)) {
				task.assignUserToTask(userEmail);
			}
		}

	}

	public void saveTaskToProject(String projectId, String taskId) {
		Project project = projectRepository.findById(projectId);
		project.addTaskUid(taskId);
		try {
			projectRepository.store(project);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void saveTask(Task task) {
		try {
			taskRepository.store(task);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public void updateTask(String projectId, String taskId,
			TaskViewModel viewModel) {
		Task task = taskRepository.findByUid(taskId);
		task.setName(viewModel.getName());

		if (!viewModel.getUserEmail().isEmpty()
				&& userRepository.findByEmail(viewModel.getUserEmail()) != null) {
			assignUserToTask(viewModel.getUserEmail(), projectId, taskId);
		}

		try {
			taskRepository.store(task);
		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public Task getTaskById(String taskId) {
		return taskRepository.findByUid(taskId);
	}

	public Project getProjectById(String projectId) {
		return projectRepository.findById(projectId);
	}

	public List<Task> getAllTasksByProjectId(String projectId) {
		Project project = projectRepository.findById(projectId);
		List<String> projectTasksId = project.getTaskUids();
		List<Task> tasks = new ArrayList<Task>();
		for (String taskId : projectTasksId) {
			Task task = taskRepository.findByUid(taskId);
			tasks.add(task);
		}
		return tasks;
	}

	public List<User> getAllEmployeesByProjectId(String projectId) {
		Project project = projectRepository.findById(projectId);
		List<String> projectEmployeesEmail = project.getEmployeeUids();
		List<User> employees = new ArrayList<User>();

		for (String employeeEmail : projectEmployeesEmail) {
			User employee = userRepository.findByEmail(employeeEmail);
			employees.add(employee);
		}
		return employees;
	}

	public List<Task> getAllTasksByCurrentUserId(String currentUserId) {
		Collection<Project> projects = projectRepository.findAll();
		List<Task> tasks = new ArrayList<Task>();

		for (Project project : projects) {
			if (project.userIsAssignedToProject(currentUserId)) {
				List<Task> projectTasks = getAllTasksByProjectId(project
						.getUid());
				tasks.addAll(projectTasks);
			}
		}
		return tasks;
	}

	public void assignUserToTask(String currentUserId, String projectId,
			String taskUId) {
		Project project = projectRepository.findById(projectId);
		Task task = taskRepository.findByUid(taskUId);

		if (task != null && project != null
				&& project.userIsAssignedToProject(currentUserId)) {
			task.assignUserToTask(currentUserId);
		} else {
			project.addEmployeeToProject(currentUserId);
			task.assignUserToTask(currentUserId);
		}
	}

	public String getTaskName(String taskUId) {
		Task task = taskRepository.findByUid(taskUId);
		return task.getName();
	}

	public void addNewTaskToProject(TaskViewModel taskViewModel,
			String projectNumber) {
		Project project = projectRepository.findById(projectNumber);

		if (project != null) {
			Task newTask = new Task(taskViewModel.getName(),
					project.getEmployeeUids());
			saveTask(newTask);
			saveTaskToProject(projectNumber, newTask.getUid());
		}
	}

	public void createProject(ProjectViewModel projectViewModel) {
		Project newProject = new Project(projectViewModel.getName(),
				projectViewModel.getTaskIds(), projectViewModel.getUserIds(),
				projectViewModel.getExpenseIds());
		addProject(newProject);

	}

	public ProjectViewModel retrieveProjectViewModelForExistingProject(
			String projectNumber) {
		ProjectViewModel projectViewModel = projectConverter
				.convert(getProjectById(projectNumber));
		projectViewModel.setAvailableUsers(extractUserEmails(projectProcessor
				.evaluateAvailableEmployeesByProject(projectNumber)));
		return projectViewModel;
	}

	public Collection<TaskViewModel> retrieveTasksByProject(String projectNumber) {
		return taskConverter.convert(getAllTasksByProjectId(projectNumber));
	}

	public Collection<UserViewModel> retieveEmployeesByProject(
			String projectNumber) {
		return userConverter.convert(getAllEmployeesByProjectId(projectNumber));
	}

	private List<String> extractUserEmails(Collection<User> users) {
		List<String> userEmails = new ArrayList<String>();

		for (User user : users) {
			userEmails.add(user.getEmail());
		}
		return userEmails;
	}
}
