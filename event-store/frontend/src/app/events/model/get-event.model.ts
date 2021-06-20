export class GetEvent {
  public name: string;
  public description: string;
  public dateFrom: Date;
  public dateTo: Date;
  public userList: string[];
  public tags: [];
  public creator: string;

  constructor(
    name: string,
    description: string,
    dateFrom: Date,
    dateTo: Date,
    userList: string[],
    tags: [],
    creator: string
  ) {
    this.name = name;
    this.description = description;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.userList = userList;
    this.tags = tags;
    this.creator = creator;
  }
}
